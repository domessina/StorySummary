package schn.beme.storysummary.synchronization;

import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.presenterhelper.Helper;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.network.OkHttpWebServiceHelper;

/**
 * Created by Dorito on 02-09-16.
 */
public class PushUserChoiceAsynchTask extends AsyncTask<Integer, Integer, Boolean> {

    private OrmLiteDatabaseHelper dbHelper;
    private Dao<Diagram,Integer> diagramDao;
    private Helper.WebService webService;

    HashMap<Integer, String> userChoices;
    SynchManager manager;

    public PushUserChoiceAsynchTask(SynchManager manager,HashMap<Integer, String> userChoices){
        this.userChoices=userChoices;
        this.manager=manager;
    }

    @Override
    protected void onPreExecute(){
        webService=new OkHttpWebServiceHelper();
        initDBAccess();
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        for(Map.Entry<Integer,String> entry:userChoices.entrySet()){
            try {
                Diagram d=diagramDao.queryForId(entry.getKey());
                Diagram d2=webService.pushUserChoice(d,entry.getValue());
                //json deserialization resolution of a minor logic problem in my code
            if(d!=null&&d2!=null){
                d.title=d2.title;
                d.pictureId=d2.pictureId;
                diagramDao.update(d);
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
    }

    @Override
    protected void onPostExecute(Boolean result){
        manager.pushUserChoiceExecuted();
    }


    private  void initDBAccess(){

        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        diagramDao=dbHelper.getDiagramDao();
    }

}
