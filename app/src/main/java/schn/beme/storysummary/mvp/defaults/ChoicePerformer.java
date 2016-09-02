package schn.beme.storysummary.mvp.defaults;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;

/**
 * Created by Dorito on 02-09-16.
 */
public class ChoicePerformer {


    OrmLiteDatabaseHelper dbHelper;
    Dao<Diagram,Integer> diagramDao;
    HashMap<Integer, String> userChoices;

    public ChoicePerformer(HashMap<Integer, String> userChoices){
        this.userChoices=userChoices;
        initDBAccess();
    }

    private  void initDBAccess(){

        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        diagramDao=dbHelper.getDiagramDao();
    }

    public void perform() throws SQLException {
        for(Map.Entry<Integer,String> entry:userChoices.entrySet()){
            Diagram d=diagramDao.queryForId(entry.getKey());
            switch (entry.getValue()){
                case "S-DELETE":  sDelete(d.id);     break;
                case "C-DELETE":  cDelete(d.id);      break;
                case "C-UPDATE":  cUpdate() ;     break;
                case "S-UPDATE":  sUpdate(d.id,d.serverId);    break;
            }
            d.needSynch=false;
            diagramDao.update(d);
        }
    }

    private void sDelete(int diagramId) throws SQLException {
        diagramDao.deleteById(diagramId);
    }


    private void cDelete(int diagramId) throws SQLException {
        diagramDao.deleteById(diagramId);
    }

    private void cUpdate(){
    }

    private void sUpdate(int idClient, int idServer){
        //isUpdate is done in SynchManager

    }


}
