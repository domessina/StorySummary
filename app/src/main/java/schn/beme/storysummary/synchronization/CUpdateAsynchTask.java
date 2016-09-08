package schn.beme.storysummary.synchronization;

import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Character;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.narrativecomponent.E_NarrativeComponent;
import schn.beme.storysummary.narrativecomponent.NarrativeComponent;
import schn.beme.storysummary.narrativecomponent.Scene;
import schn.beme.storysummary.narrativecomponent.Trait;
import schn.beme.storysummary.presenterhelper.Helper;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.network.OkHttpWebServiceHelper;

/**
 * Created by Dorito on 03-09-16.
 */
public class CUpdateAsynchTask extends AsyncTask<Integer, Integer, Boolean> {

    private OrmLiteDatabaseHelper dbHelper;
    private Dao<Diagram,Integer> diagramDao;
    private Dao<Chapter,Integer> chapterDao;
    private Dao<Scene,Integer> sceneDao;
    private Dao<Character,Integer> characterDao;
    private Dao<Trait,Integer> traitDao;
    private Helper.WebService webService;
    private List<Diagram> toUpdate;
    private SynchManager manager;


    public CUpdateAsynchTask(SynchManager manager, List<Diagram> toUpdate){
        this.manager=manager;
        this.toUpdate=toUpdate;
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
        HashMap<Integer, Integer> chapLocalSrvId;
        HashMap<Integer, Integer> charLocalSrvId;

        try{
        for(Diagram d:toUpdate){

            chapters=chapterDao.queryForEq("diagram_id",d.id);
            scenes=sceneDao.queryForEq("diagram_id",d.id);
            characters=characterDao.queryForEq("diagram_id",d.id);
            traits=traitDao.queryForEq("diagram_id",d.id);

            chapLocalSrvId=new HashMap<>(chapters.size());
            charLocalSrvId=new HashMap<>(characters.size());

            if(chapters!=null){
                for(Chapter c: chapters){
                    c.diagramIdForSync=d.serverId;
                    c.serverId=webService.postOrPutT(c,E_NarrativeComponent.NC_Chapter,true);
                    chapLocalSrvId.put(c.id,c.serverId);
                    chapterDao.update(c);
                }
            }

            if(scenes!=null){
                for(Scene s:scenes) {
                    s.diagramIdForSync=d.serverId;
                    s.chapterIdForSync=chapLocalSrvId.get(s.chapterId.id);
                    s.serverId=webService.postOrPutT(s, E_NarrativeComponent.NC_Scene, true);
                    sceneDao.update(s);
                }
            }

            if(characters!=null){
                for(Character c:characters){
                    c.diagramIdForSync=d.serverId;
                    c.serverId=webService.postOrPutT(c,E_NarrativeComponent.NC_Character,true);
                    charLocalSrvId.put(c.id,c.serverId);
                    characterDao.update(c);
                }
            }

            if(traits!=null){
                for(Trait t:traits){
                    t.diagramIdForSync=d.serverId;
                    t.characterIdForSync=charLocalSrvId.get(t.characterId.id);
                                                                        //t.serverId==-1
                    t.serverId=webService.postOrPutT(t,E_NarrativeComponent.NC_Trait,true);
                    traitDao.update(t);
                }
            }
        }


        } catch (SQLException e) {
            e.printStackTrace(); return false;}
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
    }

    @Override
    protected void onPostExecute(Boolean result){
        manager.cUpdateExecuted();
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
