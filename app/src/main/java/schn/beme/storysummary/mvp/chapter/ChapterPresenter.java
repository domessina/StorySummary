package schn.beme.storysummary.mvp.chapter;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.ResumePauseAware;
import schn.beme.storysummary.SchnException;
import schn.beme.storysummary.StartStopAware;
import schn.beme.storysummary.eventbusmsg.ClickChapterCardEvent;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.android.ActivityStarterHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogWindowHelper;

/**
 * Created by Dorito on 20-07-16.
 */
public class ChapterPresenter<V extends ChapterPresenter.View> extends DefaultActionBarPresenter<V>
        implements ConfirmDialogListener, ConfirmEditDialogListener, StartStopAware, ResumePauseAware {

    public int lastDiagramIdTouched;
    public int diagramId;
    protected OrmLiteDatabaseHelper dbHelper;
    protected Dao<Chapter,Integer> chapterDao;
    private ChapterAdapter chapterAdapter;
    private ChapterAdapter.ChapterVH selectedHolder;

    public ChapterPresenter(V view, int diagramId) {

        super(view);
        this.diagramId=diagramId;
        initDBAccess();
        chapterAdapter=new ChapterAdapter(createList());

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
        if(MyApplication.chapterToRefreshId!=-1){
            try {
                initDBAccess();
                chapterAdapter.updateContent(chapterDao.queryForId(MyApplication.chapterToRefreshId));
            } catch (SQLException|SchnException e) {
                e.printStackTrace();
            }finally {
                MyApplication.chapterToRefreshId=-1;
            }
        }
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
      dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
      chapterDao=dbHelper.getChapterDao();
    }

    @Subscribe
    public void onClickChapterCardEvent(ClickChapterCardEvent event) {
        lastDiagramIdTouched=event.chapterId;
        selectedHolder=event.holder;
        if(!event.isLong) {
//            ActivityStarterHelper.getInstance().startSceneActivity(selectedHolder.chapterId,selectedHolder.getChapterTitle(),selectedHolder.getChapterNote());
        getView().startSceneActivity(selectedHolder.chapterId,selectedHolder.getChapterTitle(),selectedHolder.getChapterNote());
        }
    }

    public List<Chapter> createList() {

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
        //sorting by position


        Collections.sort(result);
        return result;
    }


    private void updateDiagramToDB(){

        try {
            Dao<Diagram,Integer>diagramDao=dbHelper.getDiagramDao();
            Diagram d= diagramDao.queryForId(diagramId);
            d.title= getView().getDiagramTitle();
            diagramDao.update(d);
            MyApplication.diagramToRefreshId=diagramId;
            getView().showToast("Modifications saved");
        } catch (SQLException e) {
            getView().showToast("A problem occurred");
            Log.e("Error","Cannot update diagram id="+diagramId+" in database");
            e.printStackTrace();
        }
    }

    public void saveBtnClicked(){updateDiagramToDB();}

    //-----------------UPSIDE DOWN CHAPTERS----------------

    public void downUpClicked(boolean toDown, int chapterId ){

        try{ //in the next lines, we will want to move chapter from pos1, to pos2
            int positions[]=chapterAdapter.getNewFuturePositions(toDown,chapterId);

            //find in db the id and current position of both chapters
            Chapter chapMoved = findChapterMoved(chapterId);
            Chapter chapReplaced=findChapterReplaced(diagramId,positions[1]);

            //update var chapters found with the new positions
            chapMoved.position=positions[1];
            chapReplaced.position=positions[0];

            //update db chapters found with the new positions
            updatePositionChapterMoved(chapterId,chapMoved.position);
            updatePositionChapterReplaced(chapReplaced.id,chapReplaced.position);

            //update the the ChapterHolders and List of RecyclerView
            chapterAdapter.updateContent(chapMoved);
            chapterAdapter.updateContent(chapReplaced);

            //swap in the ui the chapters
            chapterAdapter.swapChapter(positions[0],positions[1]);

        }catch(IndexOutOfBoundsException|SQLException|SchnException e){
            //IndexOutofBound is when downbtn is pressed for the last item
            //or upBtn fot the first
            e.printStackTrace();
        }
    }

    private Chapter findChapterMoved(int chapterId)throws SQLException{
        return chapterDao.queryForId(chapterId);
    }

    private Chapter findChapterReplaced(int diagramId, int oldPosition) throws SQLException{
        Map<String, Object> chapterFields=new HashMap<>(2);
        chapterFields.put("diagram_id",diagramId);
        chapterFields.put("position",oldPosition);
        List<Chapter> results=chapterDao.queryForFieldValues(chapterFields);
        /*if(results.size()!=1){
            throw new SchnException("Have to be only one match with the query");
        }*/
        return results.get(0);
    }
    //the one that triggered the motion
    private void updatePositionChapterMoved(int chapterId, int newPosition) throws SQLException{
        Chapter chap1=chapterDao.queryForId(chapterId);//TODO apparement l'exception n'a lieue que quand on bouge le chapitre, va a l'intérieur, retourne en arriere et essaye à nouveau de le bouger de place
        chap1.position=newPosition;
        chapterDao.update(chap1);
    }

    private void updatePositionChapterReplaced(int chapterId, int newPosition)throws SQLException,SchnException{

        Chapter chap=chapterDao.queryForId(chapterId);
        chap.position=newPosition;
        chapterDao.update(chap);
    }

    //-----------------END UPSIDE DOWN---------------

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
        DialogWindowHelper.getInstance().showConfirm(getView().getContext(),"Are you sure?",null,this);
    }

    @Override
    public void accepted() {
        Chapter c =selectedHolder.removeCard();
        try {
            chapterDao.delete(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-----------ACTION CREATE DIAGRAM FAB---------------------

    public void addChapter()
    {
        DialogWindowHelper.getInstance().showConfirmEditText(getView().getContext(),"New Chapter", "Title",false, this);
    }

    @Override
    public void accepted(String input) {

        Chapter c=new Chapter(-1, (short)0, input,
                chapterAdapter.getItemCount(), "Write your notes",new Diagram(diagramId));
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
    }
}


