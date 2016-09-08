package schn.beme.storysummary.mvp.scenecharacters;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.ResumePauseAware;
import schn.beme.storysummary.StartStopAware;
import schn.beme.storysummary.mvp.character.CharacterAdapter;
import schn.beme.storysummary.eventbusmsg.ClickCharacterCardEvent;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.narrativecomponent.CharacterScene;
import schn.beme.storysummary.narrativecomponent.Scene;
import schn.beme.storysummary.narrativecomponent.Character;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogWindowHelper;

/**
 * Created by Dorito on 01-08-16.
 */
public class SceneCharactersPresenter<V extends SceneCharactersPresenter.View> extends DefaultActionBarPresenter<V>
        implements ResumePauseAware, StartStopAware, ConfirmEditDialogListener, ConfirmDialogListener
{
    protected int sceneId;
    protected OrmLiteDatabaseHelper dbHelper;
    protected Dao<Scene,Integer> sceneDao;
    protected Dao<Character,Integer> characterDao;
    protected Dao<CharacterScene,Void> characterSceneDao;
    private CharacterAdapter characterAdapter;
    private CharacterAdapter.CharacterVH selectedHolder;
    public int lastCharacterIdTouched;

    public SceneCharactersPresenter(V v, int sceneId){
        super(v);
        initDBAccess();
        this.sceneId=sceneId;
        characterAdapter=new CharacterAdapter(createList());
    }

    @Override
    public void onStart() {
        try {//in case of subscriver is already registered
            initDBAccess();
        }
        catch(EventBusException e){
            e.printStackTrace();
            Log.e("Error","see EventBus stackTrace");
        }
       /* if(MyApplication.sceneToRefreshId!=-1){
            try {
                initDBAccess();
                characterAdapter.refreshCard(characterDao.queryForId(MyApplication.characterToRefreshId));

            } catch (SQLException|SchnException e) {
                e.printStackTrace();
            }finally {
                MyApplication.sceneToRefreshId=-1;
            }
        }*/
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


    public List<Character> createList() {

        List<Character> result=new ArrayList<>();
        List<CharacterScene> cs=null;

        try {
            cs=characterSceneDao.queryForEq("scene_id",sceneId);
            for(CharacterScene cs1:cs){
            result.add(characterDao.queryForId(cs1.characterId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }

        return result;
    }

    @Subscribe
    public void onClickCharacterCardEvent(ClickCharacterCardEvent event) {
        if(event.isLong){
        lastCharacterIdTouched=event.characterId;
        selectedHolder=event.holder;}
    }


    //-----------ACTION DELETE CONTEXTUAL MENU-----------


    @Override
    public void accepted() {
        Character c =selectedHolder.removeCard();
        try {
            characterSceneDao.delete(new CharacterScene(c.id,sceneId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-----------ACTION CREATE DIAGRAM FAB---------------------

    public void addCharacter()
    {
        DialogWindowHelper.getInstance().showConfirmEditText(getView().getContext(),"New link scene/character", "Name of the character",false, this);
    }
    @Override
    public void accepted(String input) {

        try {
            CharacterScene cs= new CharacterScene();
//            List<Character> cl=characterDao.queryForEq("name",input);
            QueryBuilder<Character,Integer> queryBuilder=characterDao.queryBuilder();
            queryBuilder.where().eq("name",input).and().eq("diagram_id",MyApplication.workingDiagramId);
            PreparedQuery<Character> preparedQuery = queryBuilder.prepare();
            Character c=characterDao.queryForFirst(preparedQuery);
            if(c!=null){
                cs.characterId=c.id;
                cs.sceneId=sceneId;
                characterSceneDao.create(cs);
                characterAdapter.addCharacterCard(c);
            }
            else{
                getView().showToast("Does not exist in this diagram");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        getView().scrollToEnd();
    }

    @Override
    public void canceled() {

    }

    public CharacterAdapter getCharacterAdapter(){return this.characterAdapter;}

    public int getLastPositionTotal()
    {
        return characterAdapter.getItemCount()-1;
    }



    private  void initDBAccess(){

        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        sceneDao=dbHelper.getSceneDao();
        characterDao=dbHelper.getCharacterDao();
        characterSceneDao=dbHelper.getCharacterSceneDao();
    }

    public interface View extends DefaultActionBarPresenter.View{
        String getSceneTitle();
        String getSceneNote();
        void scrollToEnd();

    }
}
