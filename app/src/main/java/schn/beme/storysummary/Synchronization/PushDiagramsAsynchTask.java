package schn.beme.storysummary.synchronization;

import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.presenterhelper.Helper;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.network.OkHttpWebServiceHelper;

/**
 * Created by Dorito on 02-09-16.
 */
public class PushDiagramsAsynchTask extends AsyncTask<Integer, Integer, Boolean> {

    private OrmLiteDatabaseHelper dbHelper;
    private Dao<Diagram,Integer> diagramDao;
    public List<Diagram> diagrams;
    public List<ActionDoneResponse> actionsDone;
    private Helper.WebService webService;
    private SynchManager manager;

    public PushDiagramsAsynchTask(SynchManager manager){
        this.manager=manager;
    }

    @Override
    protected void onPreExecute(){
        webService=new OkHttpWebServiceHelper();
        initDBAccess();
    }

    @Override
    protected Boolean doInBackground(Integer... values) {

        try {
            diagrams=diagramDao.queryForEq("need_synch",true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pushDiagrams();

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
    }

    @Override
    protected void onPostExecute(Boolean result){

        try {
            manager.pushDiagramsExecuted();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pushDiagrams(){
        actionsDone=new ArrayList<>();
        for(Diagram d: diagrams){
            actionsDone.add(webService.pushDiagram(d,d.action));
        }

    }

    private  void initDBAccess(){

        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        diagramDao=dbHelper.getDiagramDao();
    }

}
