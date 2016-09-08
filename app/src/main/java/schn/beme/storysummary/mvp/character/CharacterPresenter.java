package schn.beme.storysummary.mvp.character;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.ResumePauseAware;
import schn.beme.storysummary.SchnException;
import schn.beme.storysummary.StartStopAware;
import schn.beme.storysummary.eventbusmsg.ClickChapterCardEvent;
import schn.beme.storysummary.eventbusmsg.ClickCharacterCardEvent;
import schn.beme.storysummary.mvp.chapter.ChapterAdapter;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Character;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogWindowHelper;

/**
 * Created by Dorito on 07-09-16.
 */
public class CharacterPresenter<V extends CharacterPresenter.View> extends DefaultActionBarPresenter<V>
    implements ConfirmDialogListener, ConfirmEditDialogListener,StartStopAware, ResumePauseAware
{

    public int lastCharIdTouched;
    protected OrmLiteDatabaseHelper dbHelper;
    protected Dao<Character,Integer> characterDao;
    private CharacterAdapter charAdapter;
    private CharacterAdapter.CharacterVH selectedHolder;

    public CharacterPresenter(V view) {

        super(view);
        initDBAccess();
        charAdapter=new CharacterAdapter(createList());

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
        if(MyApplication.characterToRefreshId!=-1){
            try {
                initDBAccess();
                charAdapter.updateContent(characterDao.queryForId(MyApplication.characterToRefreshId));
            } catch (SQLException|SchnException e) {
                e.printStackTrace();
            }finally {
                MyApplication.characterToRefreshId=-1;
            }
        }
    }

    @Override
    public void onResume() {
        if(characterDao==null){
            initDBAccess();
        }
    }

    @Override
    public void onPause() {
   /*     OpenHelperManager.releaseHelper();
        dbHelper=null;
        characterDao=null;*/
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onClickCharacterCardEvent(ClickCharacterCardEvent event) {
        lastCharIdTouched=event.characterId;
        selectedHolder=event.holder;
        if(!event.isLong) {
            getView().startCharacterTraitsActivity(lastCharIdTouched);
        }
    }

    public List<Character> createList() {

        List<Character> result= new ArrayList<>();

        try {
            characterDao=dbHelper.getCharacterDao();
            result=characterDao.queryForEq("diagram_id", MyApplication.workingDiagramId);
//                result=chapterDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public CharacterAdapter getCharacterAdapter(){return this.charAdapter;}

    public int getLastPositionTotal()
    {
        return charAdapter.getItemCount()-1;
    }


    //-----------ACTION DELETE CONTEXTUAL MENU-----------


    public void contextMenuDelete()
    {
        DialogWindowHelper.getInstance().showConfirm(getView().getContext(),"Are you sure?",null,this);
    }

    @Override
    public void accepted() {
        Character c =selectedHolder.removeCard();
        try {
            characterDao.delete(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void canceled() {

    }
    //-----------ACTION CREATE CHARACTER FAB---------------------

    public void addCharacter()
    {
        DialogWindowHelper.getInstance().showConfirmEditText(getView().getContext(),"New Character", "Name",false, this);
    }

    @Override
    public void accepted(String input) {

        Character c= new Character();
        c.diagramId= new Diagram(MyApplication.workingDiagramId);
        c.name=input;
        try {
            characterDao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        charAdapter.addCharacterCard(c);
        getView().scrollToEnd();
    }

    private  void initDBAccess(){
        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        characterDao=dbHelper.getCharacterDao();
    }

    public interface View extends DefaultActionBarPresenter.View
    {
        void scrollToEnd();
    }
}
