package schn.beme.storysummary.synchronization;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Character;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.narrativecomponent.Scene;
import schn.beme.storysummary.narrativecomponent.Trait;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;

/**
 * Created by Dorito on 02-09-16.
 */
public class ChoicePerformer {


    private OrmLiteDatabaseHelper dbHelper;
    private Dao<Diagram,Integer> diagramDao;
    private Dao<Chapter,Integer> chapterDao;
    private Dao<Scene,Integer> sceneDao;
    private Dao<Character,Integer> characterDao;
    private Dao<Trait,Integer> traitDao;
    private HashMap<Integer, String> userChoices;
    public List<Diagram> sUpdateD;
    public List<Diagram> cUpdateD;

    public ChoicePerformer(HashMap<Integer, String> userChoices){
        this.userChoices=userChoices;
        sUpdateD=new ArrayList<>();
        cUpdateD=new ArrayList<>();
        initDBAccess();
    }

    private  void initDBAccess(){

        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        diagramDao=dbHelper.getDiagramDao();
        chapterDao=dbHelper.getChapterDao();
        sceneDao=dbHelper.getSceneDao();
        characterDao=dbHelper.getCharacterDao();
        traitDao=dbHelper.getTraitDao();
    }

    public void perform() throws SQLException {
        for(Map.Entry<Integer,String> entry:userChoices.entrySet()){
            Diagram d=diagramDao.queryForId(entry.getKey());
            switch (entry.getValue()){
                case "S-DELETE":  sDelete(d);     break;
                case "C-DELETE":  cDelete(d);      break;
                case "C-UPDATE":  cUpdate(d) ;     break;
                case "S-UPDATE":  sUpdate(d);    break;
            }
        }
    }

    private void sDelete(Diagram d) throws SQLException {
        d.enabled=false;
        diagramDao.update(d);
    }


    private void cDelete(Diagram d) throws SQLException {
//        diagramDao.deleteById(diagramId);
        d.needSynch=false;
        diagramDao.update(d);
    }

    private void cUpdate(Diagram d){
        cUpdateD.add(d);
    }

    private void sUpdate(Diagram d) throws SQLException {

        UpdateBuilder<Diagram, Integer> updateBuilder = diagramDao.updateBuilder();
        updateBuilder.where().eq("id", d.id);
        updateBuilder.updateColumnValue("enabled",true);
        updateBuilder.update();
        sUpdateD.add(d);
        try {
            List<Chapter> chapters=chapterDao.queryForEq("diagram_id",d.id);
            chapterDao.delete(chapters);
            chapters=null;
            List<Scene> scenes=sceneDao.queryForEq("diagram_id",d.id);
            sceneDao.delete(scenes);
            scenes=null;
            List<Character> characters=characterDao.queryForEq("diagram_id",d.id);
            characterDao.delete(characters);
            List<Trait> traits=traitDao.queryForEq("diagram_id",d.id);
            traitDao.delete(traits);
            traits=null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
