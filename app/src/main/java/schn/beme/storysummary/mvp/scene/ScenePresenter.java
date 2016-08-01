package schn.beme.storysummary.mvp.scene;

import android.os.AsyncTask;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.ResumeAndPauseAware;
import schn.beme.storysummary.StartAndStopAware;
import schn.beme.storysummary.eventbusmsg.ClickSceneCardEvent;
import schn.beme.storysummary.mvp.chapter.Chapter;
import schn.beme.storysummary.mvp.chapter.ChapterAdapter;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.presenterhelper.DatabaseHelper;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogHelper;

/**
 * Created by Dorito on 25-07-16.
 */
public class ScenePresenter<V extends ScenePresenter.View> extends DefaultActionBarPresenter<V>
        implements ResumeAndPauseAware, StartAndStopAware, ConfirmDialogListener,ConfirmEditDialogListener{



    public int chapterId;
    protected DatabaseHelper dbHelper;
    protected Dao<Scene,Integer> sceneDao;
    private SceneAdapter sceneAdapter;
    private SceneAdapter.SceneVH selectedHolder;

    public ScenePresenter(V view, int chapterId) {

        super(view);
        this.chapterId=chapterId;
        initDBAccess();
        sceneAdapter=new SceneAdapter(createList(3));
    }


    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }


    @Override
    public void onResume() {
        if(sceneDao ==null){
            initDBAccess();
        }
    }

    @Override
    public void onPause() {

        OpenHelperManager.releaseHelper();
        dbHelper=null;
        sceneDao =null;
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }


    private  void initDBAccess(){
        try {
            dbHelper = OpenHelperManager.getHelper(MyApplication.getCrntActivityContext(), DatabaseHelper.class);
            sceneDao=dbHelper.getSceneDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Subscribe
    public void onClickSceneCardEvent(ClickSceneCardEvent event) {

        selectedHolder=event.holder;
        if(!event.isLong) {
            IntentHelper.getInstance().startSceneCharactersActivity(event.sceneId);
        }
    }

    private List<Scene> createList(int nb){
        List<Scene> result=null;
        try {
            sceneDao=dbHelper.getSceneDao();
            result=sceneDao.queryForEq("chapter_id",chapterId);
//                result=chapterDao.queryForAll();

        } catch (NullPointerException|SQLException e) {
            e.printStackTrace();
        }
        //sorting by position
        Comparator<Scene> comparator = new Comparator<Scene>() {
            @Override
            public int compare(Scene chapter, Scene t1) {
                return chapter.position-t1.position;
            }
        };
        return result;
    }

    //----------ADDING SCENE-------
    public void addScene(){
        DialogHelper.showConfirmEditText("New Scene", "Title",false, this);
    }

    @Override
    public void accepted(String input) {

        Scene s=new Scene(-1,new Chapter(chapterId), sceneAdapter.getItemCount()
                , input, "Write your notes","");
        sceneAdapter.addSceneCard(s);
        try {
            sceneDao.create(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getView().scrollToEnd();
    }
    //----------END ADDING---------

    private void updateChapterToDB(){

        try {
            Dao<Chapter,Integer> chapterDao=dbHelper.getChapterDao();
            Chapter c= chapterDao.queryForId(chapterId);
            c.title= getView().getChapterTitle();
            c.note=getView().getChapterNote();
            chapterDao.update(c);
            MyApplication.chapterToRefreshId=chapterId;
            getView().showToast("Modifications saved");

        } catch (SQLException e) {
            getView().showToast("A problem occurred");
            Log.e("Error","Cannot update diagram id="+chapterId+" in database");
            e.printStackTrace();
        }
    }


    //-----------ACTION DELETE CONTEXTUAL MENU-----------


    public void contextMenuDelete()
    {
        DialogHelper.showConfirm("Are you sure?",null,this);
    }

    @Override
    public void canceled() {

    }

    @Override
    public void accepted() {
        Scene s =selectedHolder.removeCard();
        /*try {
            sceneDao.delete(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    //---------END ACTION DELETE CONTEXTUAL MENU------
    public SceneAdapter getSceneAdapter(){return this.sceneAdapter;}


    public void saveBtnClicked(){updateChapterToDB();}

    public int getLastPositionTotal()
    {
        return sceneAdapter.getItemCount()-1;
    }



    public interface View extends DefaultActionBarPresenter.View {
        void showToast(String msg);
        String getChapterTitle();
        String getChapterNote();
        void scrollToEnd();
    }
}
