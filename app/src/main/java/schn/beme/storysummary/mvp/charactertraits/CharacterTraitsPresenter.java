package schn.beme.storysummary.mvp.charactertraits;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.ResumePauseAware;
import schn.beme.storysummary.StartStopAware;
import schn.beme.storysummary.eventbusmsg.ClickChapterCardEvent;
import schn.beme.storysummary.eventbusmsg.ClickTraitCardEvent;
import schn.beme.storysummary.mvp.chapter.ChapterAdapter;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Character;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.narrativecomponent.Trait;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogWindowHelper;

/**
 * Created by Dorito on 07-09-16.
 */
public class CharacterTraitsPresenter<V extends CharacterTraitsPresenter.View> extends DefaultActionBarPresenter<V>
        implements ConfirmDialogListener, ConfirmEditDialogListener {

    private int lastTraitIdTouched;
    public int characterId;
    protected OrmLiteDatabaseHelper dbHelper;
    protected Dao<Trait,Integer> traitDao;
    private TraitAdapter traitAdapter;
    private TraitAdapter.TraitVH selectedHolder;


    public CharacterTraitsPresenter(V view, int charId) {

        super(view);
        this.characterId=charId;
        initDBAccess();
        traitAdapter=new TraitAdapter(createList());
        EventBus.getDefault().register(this);
    }

    private  void initDBAccess(){
        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        traitDao=dbHelper.getTraitDao();
    }

    @Subscribe
    public void onClickChapterCardEvent(ClickTraitCardEvent event) {
        lastTraitIdTouched=event.traitId;
        selectedHolder=event.holder;
    }

    public List<Trait> createList() {
        List<Trait> result=null;
        try {
            traitDao=dbHelper.getTraitDao();
            result=traitDao.queryForEq("character_id",new Character(characterId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void updateCharacterToDB(){

        try {
            Dao<Character,Integer>characterDao=dbHelper.getCharacterDao();
            Character d= characterDao.queryForId(characterId);
            d.name= getView().getCharacterName();
            d.type= getView().getCharacterType();
            d.note= getView().getCharacterNote();
            characterDao.update(d);
            MyApplication.characterToRefreshId=characterId;
            getView().showToast("Modifications saved");
        } catch (SQLException e) {
            getView().showToast("A problem occurred");
            Log.e("Error","Cannot update character id="+characterId+" in database");
            e.printStackTrace();
        }
    }

    public Character getCharacter(int characterId){
        Dao<Character,Integer>characterDao=dbHelper.getCharacterDao();
        try {
            return characterDao.queryForId(characterId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveBtnClicked(){updateCharacterToDB();}

    public TraitAdapter getTraitAdapter(){return this.traitAdapter;}



    public int getLastPositionTotal()
    {
        return traitAdapter.getItemCount()-1;
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
        Trait c =selectedHolder.removeCard();
        try {
            traitDao.delete(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-----------ACTION CREATE DIAGRAM FAB---------------------

    public void addTrait()
    {
        DialogWindowHelper.getInstance().showConfirmEditText(getView().getContext(),"New Trait", "Name",false, this);
    }

    @Override
    public void accepted(String input) {

        Trait t=new Trait();
        t.name=input;
        t.characterId=new Character(characterId);
        traitAdapter.addTraitCard(t);
        try {
            traitDao.create(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getView().scrollToEnd();
    }

    //--------------END CREATE DB-------------

    public interface View extends DefaultActionBarPresenter.View
    {
        void scrollToEnd();
        String getCharacterName();
        String getCharacterType();
        String getCharacterNote();
    }
}
