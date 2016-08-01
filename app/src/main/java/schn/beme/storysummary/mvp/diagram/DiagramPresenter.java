package schn.beme.storysummary.mvp.diagram;


import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.ResumeAndPauseAware;
import schn.beme.storysummary.SchnException;
import schn.beme.storysummary.presenterhelper.DatabaseHelper;
import schn.beme.storysummary.eventbusmsg.ClickDiagramCardEvent;
import schn.beme.storysummary.mvp.chapter.ChapterActivity;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.StartAndStopAware;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;

/**
 * Created by Dorito on 11-07-16.
 */
public class DiagramPresenter<V extends DiagramPresenter.View> extends DefaultActionBarPresenter<V> implements StartAndStopAware, ResumeAndPauseAware, ConfirmDialogListener, ConfirmEditDialogListener {

    private DiagramAdapter diagramAdapter;
    private DiagramAdapter.DiagramCardVH selectedHolder;
    public int lastDiagramIdTouched;
    protected DatabaseHelper dbHelper;
    protected Dao<Diagram,Integer> diagramDao;

    public DiagramPresenter(V view) {

        super(view);
        initDBAccess();
        diagramAdapter=new DiagramAdapter(createList());
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);//TODO quand utiliser unregister(this) pour les presenters? car dans les fragments et ac c'est dans onstart et onstop
        if(MyApplication.diagramToRefreshId!=-1){
            try {
                initDBAccess();
                diagramAdapter.refreshCard(diagramDao.queryForId(MyApplication.diagramToRefreshId));
            } catch (SQLException|SchnException e) {
                e.printStackTrace();
            }finally {
                MyApplication.diagramToRefreshId=-1;
            }
        }
    }

    @Override
    public void onResume() {
        //if there is multiple threads who access this method,
        //follow the doc (4.1 android basics .2),
        if (diagramDao == null) {initDBAccess();}
    }

    @Override
    public void onPause() {
        //each openhelpermanager.getHelper have to be associated with a
        // releaseHelper(), see doc of this method
        OpenHelperManager.releaseHelper();
        dbHelper=null;
        diagramDao=null;
    }


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    private  void initDBAccess(){
        try {
            dbHelper = OpenHelperManager.getHelper(MyApplication.getCrntActivityContext(), DatabaseHelper.class);
            diagramDao=dbHelper.getDiagramDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onClickDiagramCardEvent(ClickDiagramCardEvent event)
    {

        lastDiagramIdTouched=event.diagramId;
        selectedHolder=event.holder;
        if(!event.isLong){
            IntentHelper.getInstance().startChapterActivity(event.diagramId,event.diagramTitle);
        }
    }


    public List<Diagram> createList() {

        List<Diagram> result=null;
      /*  List<Diagram> result = new ArrayList<>();
        for (int i=1; i <= size; i++) {

            result.add(new Diagram(i,"caca"+i,0));
        }*/

        try {
            diagramDao=dbHelper.getDiagramDao();
            result =diagramDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }




    public DiagramAdapter getDiagramAdapter()
    {
        return  this.diagramAdapter;
    }

    //-----------ACTION DELETE CONTEXTUAL MENU-----------


    public void contextMenuDelete()
    {
        DialogHelper.showConfirm("Are you sure?",null,this);
    }

    @Override
    public void accepted() {
        Integer inte=(Integer)selectedHolder.removeCard();
        try {
            diagramDao.deleteById(inte);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //-----------ACTION CREATE DIAGRAM FAB---------------------

    public void addDiagram()
    {
        DialogHelper.showConfirmEditText("New Diagram", "Title",false, this);
    }

    @Override
    public void accepted(String input) {

        Diagram d=new Diagram(-1,input,0);//even if i set 9000 ormlite changes the id during create()
        try {
            diagramDao.create(d);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        diagramAdapter.addDiagramCard(d);
        getView().scrollToEnd();
    }

    //-----------------------------------------------

    public int getLastPositionTotal()
    {
        return diagramAdapter.getItemCount()-1;
    }

    @Override
    public void canceled(){}



    public interface View extends DefaultActionBarPresenter.View
    {
        void scrollToEnd();
    }


}
