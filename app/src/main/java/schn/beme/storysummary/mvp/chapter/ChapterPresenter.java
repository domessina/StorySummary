package schn.beme.storysummary.mvp.chapter;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.RemovableCardVH;
import schn.beme.storysummary.ResumeAndPauseAware;
import schn.beme.storysummary.SchnException;
import schn.beme.storysummary.StartAndStopAware;
import schn.beme.storysummary.eventbusmsg.ClickChapterCardEvent;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.mvp.diagram.Diagram;
import schn.beme.storysummary.presenterhelper.DatabaseHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogHelper;

/**
 * Created by Dorito on 20-07-16.
 */
public class ChapterPresenter<V extends ChapterPresenter.View> extends DefaultActionBarPresenter<V>
        implements ConfirmDialogListener, ConfirmEditDialogListener, StartAndStopAware, ResumeAndPauseAware {

    public int lastDiagramIdTouched;
    public int diagramId;
    protected DatabaseHelper dbHelper;
    protected Dao<Chapter,Integer> chapterDao;
    private ChapterAdapter chapterAdapter;
    private RemovableCardVH selectedHolder;

    public ChapterPresenter(V view, int diagramId) {

        super(view);
        this.diagramId=diagramId;
        initDBAccess();
        chapterAdapter=new ChapterAdapter(createList(21));

    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);       //TODO quand utiliser unregister(this) pour les presenters? car dans les fragments et ac c'est dans onstart et onstop
    }

    @Override
    public void onResume() {
        if(chapterDao==null){
            initDBAccess();
        }
    }

    @Override
    public void onPause() {

        OpenHelperManager.releaseHelper();
        dbHelper=null;
        chapterDao=null;
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }


    private  void initDBAccess(){
        try {
            dbHelper = OpenHelperManager.getHelper(MyApplication.getCrntActivityContext(), DatabaseHelper.class);
            chapterDao=dbHelper.getChapterDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onClickChapterCardEvent(ClickChapterCardEvent event) {
        lastDiagramIdTouched=event.chapterId;
        selectedHolder=event.holder;
        if(!event.isLong) {
//            IntentHelper.getInstance().startActivityNoFlags(ChapterActivity.class);
//            chapterAdapter.moveChapter(11,12);
        }
    }

    public List<Chapter> createList(int size) {

        List<Chapter> result=null; /*= new ArrayList<>();
        for (int i=1; i <= size; i++) {

            result.add(new Chapter(i,(short)0,"ttie"+i,i,i,"notes"));
        }*/
        try {
            chapterDao=dbHelper.getChapterDao();
            result=chapterDao.queryForEq("diagram_id",diagramId);
//                result=chapterDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    private void updateDiagramToDB(){

        try {
            Dao<Diagram,Integer>diagramDao=dbHelper.getDiagramDao();
            Diagram d= new Diagram(diagramId);
            d.title= getView().getDiagramTitle();
            diagramDao.update(d);
            MyApplication.diagramToRefreshId=diagramId;
            getView().showToast("Modifications saved");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveBtnClicked(){updateDiagramToDB();}

    public void downUpClicked(boolean toDown, int chapterId ){

        try{
            int positions[]= chapterAdapter.moveChapter(toDown,chapterId);

            int diagramId=updatePositionChapterMoved(chapterId,positions[1]);
            updatePositionChapterReplaced(diagramId,positions[0]);
            chapterAdapter.notifyItemChanged(positions[0]);
            chapterAdapter.notifyItemChanged(positions[1]);

        }catch(IndexOutOfBoundsException|SQLException|SchnException e){
            //IndexOutofBound is when downbtn is pressed for the last item
            //or upBtn fot the first
            e.printStackTrace();
        }
    }

    private int updatePositionChapterMoved(int chapterId, int newPosition) throws SQLException{
        Chapter chap1=chapterDao.queryForId(chapterId);
        chap1.position=newPosition;
        chapterDao.update(chap1);
        return chap1.diagramId.id;
    }

    private void updatePositionChapterReplaced(int diagramId, int newPosition)throws SQLException,SchnException{
        Map<String, Object> chapterFields=new HashMap<>(2);
        chapterFields.put("diagram_id",diagramId);
        chapterFields.put("position",newPosition);
        List<Chapter> results=chapterDao.queryForFieldValues(chapterFields);
        if(results.size()>1){
            throw new SchnException("Have to be only one match with the query");
        }
        Chapter chap2=results.get(0);
        chap2.position=newPosition;
        chapterDao.update(chap2);
    }


    public ChapterAdapter getChapterAdapter(){return this.chapterAdapter;}



    public int getLastPositionTotal()
    {
        return chapterAdapter.getItemCount()-1;
    }

    @Override
    public void canceled() {}

    //-----------ACTION DELETE CONTEXTUAL MENU-----------


    public void contextMenuDelete()
    {
        DialogHelper.showConfirm("Are you sure?",null,this);
    }

    @Override
    public void accepted() {
        Chapter c =(Chapter)selectedHolder.removeCard();
        try {
            chapterDao.delete(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-----------ACTION CREATE DIAGRAM FAB---------------------

    public void addChapter()
    {
        DialogHelper.showConfirmEditText("New Chapter", "Title",false, this);
    }

    @Override
    public void accepted(String input) {

        Chapter c=new Chapter(-1,(short)0,input,0,"note",new Diagram(diagramId));
        chapterAdapter.addChapterCard(c);
        try {
            chapterDao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getView().scrollToEnd();
    }


    public interface View extends DefaultActionBarPresenter.View
    {
        void scrollToEnd();
        String getDiagramTitle();
        void showToast(String text);
    }
}


