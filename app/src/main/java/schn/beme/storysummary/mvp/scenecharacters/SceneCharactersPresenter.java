package schn.beme.storysummary.mvp.scenecharacters;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.ResumePauseAware;
import schn.beme.storysummary.StartStopAware;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.mvp.sectionchoice.SectionChoicePresenter;
import schn.beme.storysummary.narrativecomponent.Scene;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;

/**
 * Created by Dorito on 01-08-16.
 */
public class SceneCharactersPresenter<V extends SceneCharactersPresenter.View> extends DefaultActionBarPresenter<V>
        implements ResumePauseAware
{
    protected int sceneId;
    protected OrmLiteDatabaseHelper dbHelper;
    protected Dao<Scene,Integer> sceneDao;

    public SceneCharactersPresenter(V v, int sceneId){
        super(v);
        initDBAccess();
        this.sceneId=sceneId;
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

    public void saveBtnClicked(){
        updateSceneToDB();
    }

    private void updateSceneToDB(){

        try {

            Scene s= sceneDao.queryForId(sceneId);
            s.title= getView().getSceneTitle();
            s.note=getView().getSceneNote();
//            s.picture=getView().getPictureUrl();
            sceneDao.update(s);
            MyApplication.sceneToRefreshId=sceneId;
            getView().showToast("Modifications saved");

        } catch (SQLException e) {
            getView().showToast("A problem occurred");
            Log.e("Error","Cannot update scene id="+sceneId+" in database");
            e.printStackTrace();
        }
    }

    private  void initDBAccess(){

        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        sceneDao=dbHelper.getSceneDao();
    }

    public interface View extends DefaultActionBarPresenter.View{
        String getSceneTitle();
        String getSceneNote();

    }
}
