package schn.beme.storysummary.mvp.diagram;


import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.ResumePauseAware;
import schn.beme.storysummary.SchnException;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.eventbusmsg.ClickDiagramCardEvent;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.StartStopAware;
import schn.beme.storysummary.presenterhelper.android.ActivityStarterHelper;
import schn.beme.storysummary.presenterhelper.data.SharedPreferencesHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogWindowHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;

/**
 * Created by Dorito on 11-07-16.
 */
public class DiagramPresenter<V extends DiagramPresenter.View> extends DefaultActionBarPresenter<V> implements StartStopAware, ResumePauseAware, ConfirmDialogListener, ConfirmEditDialogListener {

    private DiagramAdapter diagramAdapter;
    private DiagramAdapter.DiagramCardVH selectedHolder;
    public int lastDiagramIdTouched;
    protected OrmLiteDatabaseHelper dbHelper;
    protected Dao<Diagram,Integer> diagramDao;

    public DiagramPresenter(V view) {

        super(view);
        initDBAccess();
        MyApplication.userId= SharedPreferencesHelper.getInstance().getUserId();
        diagramAdapter=new DiagramAdapter(createList());
    }

    @Override
    public void onStart() {
        try {//in case of subscriver is already registered
            EventBus.getDefault().register(this);
        }
        catch(EventBusException e){
            e.printStackTrace();
            Log.e("Error","see EventBus stackTrace");
        }
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

            dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
            diagramDao=dbHelper.getDiagramDao();
    }

    @Subscribe
    public void onClickDiagramCardEvent(ClickDiagramCardEvent event)
    {

        lastDiagramIdTouched=event.diagramId;
        selectedHolder=event.holder;
        if(!event.isLong){

            MyApplication.workingDiagramId=lastDiagramIdTouched;
            try {
                Diagram d=diagramDao.queryForId(event.diagramId);
                d.action="UPDATE";
                d.needSynch=true;
                diagramDao.update(d);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ActivityStarterHelper.getInstance().startChapterActivity(event.diagramId,event.diagramTitle);
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
            result=diagramDao.queryForEq("enabled",true);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(0);
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
        DialogWindowHelper.getInstance().showConfirm("Are you sure?",null,this);
    }

    @Override
    public void accepted() {
        Integer inte=(Integer)selectedHolder.removeCard();
        try {
            Diagram d=diagramDao.queryForId(inte);
            d.enabled=false;
            d.needSynch=true;
            d.action="DELETE";
            diagramDao.update(d);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //-----------ACTION CREATE DIAGRAM FAB---------------------

    public void addDiagram()
    {
        DialogWindowHelper.getInstance().showConfirmEditText("New Diagram", "Title",false, this);
    }

    @Override
    public void accepted(String input) {

        Diagram d=new Diagram(-1,input,MyApplication.userId);//even if i set 9000 ormlite changes the id during create()
        try {
            d.action="CREATE";
            d.needSynch=true;
            d.enabled=true;
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
