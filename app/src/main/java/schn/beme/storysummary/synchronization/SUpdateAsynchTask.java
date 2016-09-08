package schn.beme.storysummary.synchronization;

import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Character;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.narrativecomponent.E_NarrativeComponent;
import schn.beme.storysummary.narrativecomponent.Scene;
import schn.beme.storysummary.narrativecomponent.Trait;
import schn.beme.storysummary.presenterhelper.Helper;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.network.OkHttpWebServiceHelper;

/**
 * Created by Dorito on 03-09-16.
 */
public class SUpdateAsynchTask extends AsyncTask<Integer, Integer, Boolean> {

    private OrmLiteDatabaseHelper dbHelper;
    private Dao<Diagram,Integer> diagramDao;
    private Dao<Chapter,Integer> chapterDao;
    private Dao<Scene,Integer> sceneDao;
    private Dao<Character,Integer> characterDao;
    private Dao<Trait,Integer> traitDao;
    private Helper.WebService webService;
    private List<Diagram> sUpdateD;
    private SynchManager manager;


    public SUpdateAsynchTask(SynchManager manager, List<Diagram> sUpdateD){
        this.manager=manager;
        this.sUpdateD=sUpdateD;
    }

    @Override
    protected void onPreExecute(){
        webService=new OkHttpWebServiceHelper();
        initDBAccess();
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        List<Chapter> chapters=null;
        List<Scene> scenes=null;
        List<Character> characters=null;
        List<Trait> traits=null;

        try{
        for(Diagram d:sUpdateD){

            //it deletes chapters and scenes with cascade
            DeleteBuilder<Chapter, Integer> deleteBuilder = chapterDao.deleteBuilder();
            deleteBuilder.where().eq("diagram_id",d.id);
            deleteBuilder.delete();
            //it deletes characters and traits with cascade
            DeleteBuilder<Character, Integer> deleteBuilder2 = characterDao.deleteBuilder();
            deleteBuilder2.where().eq("diagram_id",d.id);
            deleteBuilder2.delete();

            chapters=webService.getAllTByDiagram(d.serverId, E_NarrativeComponent.NC_Chapter,Chapter.class);
            scenes=webService.getAllTByDiagram(d.serverId,E_NarrativeComponent.NC_Scene,Scene.class);
            characters=webService.getAllTByDiagram(d.serverId,E_NarrativeComponent.NC_Character,Character.class);
            traits=webService.getAllTByDiagram(d.serverId,E_NarrativeComponent.NC_Trait,Trait.class);

            //if there is no chapters for the diagram on server side, the list would be null
            if(chapters!=null){
                for(Chapter c:chapters){
                    c.diagramId=new Diagram(d.id);
                    chapterDao.create(c);
                }
            }
            if(scenes!=null){
                for(Scene s:scenes){
                    s.diagramId=new Diagram(d.id);
                    sceneDao.create(s);
                }
            }
            if(characters!=null){
                for(Character c:characters){
                    c.diagramId=new Diagram(d.id);
                    characterDao.create(c);
                }
            }
            if(traits!=null){
                for(Trait t:traits){
                    t.diagramId=new Diagram(d.id);
                    traitDao.create(t);
                }
            }
        }


            //TODO si apres les tests il n'y a plus de probleme avec la obucle for au dessus, y mettre le contenu de la boucle ci dessous
            for(Diagram d:sUpdateD){
//                d.needSynch=false;
                //it's done in pushUserChoiceAT
//                diagramDao.update(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
    }

    @Override
    protected void onPostExecute(Boolean result){
        manager.sUpdateExecuted();
    }


    private  void initDBAccess(){

        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        diagramDao=dbHelper.getDiagramDao();
        chapterDao=dbHelper.getChapterDao();
        sceneDao=dbHelper.getSceneDao();
        characterDao=dbHelper.getCharacterDao();
        traitDao=dbHelper.getTraitDao();
    }

}
