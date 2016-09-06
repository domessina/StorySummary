package schn.beme.storysummary.synchronization;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.mvp.defaults.DefaultPresenter;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogWindowHelper;

/**
 * Created by Dorito on 02-09-16.
 */
public class SynchManager implements ConfirmDialogListener {

    PushDiagramsAsynchTask pushDAT;
    PushUserChoiceAsynchTask pushUCAT;
    SUpdateAsynchTask sUpdateAT;
    CUpdateAsynchTask cUpDelToSrvAT;
    List<ActionDoneResponse> actionsDone;
    List<Diagram> diagrams;
    List<Diagram> sUpdateD;
    List<Diagram> toUpdateToSrvD;
    private OrmLiteDatabaseHelper dbHelper;
    private Dao<Diagram,Integer> diagramDao;
    HashMap<Integer,String> userChoices;
    ChoicePerformer choicePerformer;
    Context actContext;

    int indexList;

    public void synchronise(Context actContext){
        pushDAT=new PushDiagramsAsynchTask(this);
        this.actContext=actContext;
        initDBAccess();
        pushDAT.execute();
    }

    public void pushDiagramsExecuted() throws SQLException {

        actionsDone=pushDAT.actionsDone;
        diagrams =pushDAT.diagrams;
        pushDAT=null;
        indexList=0;
        userChoices = new HashMap<>(diagrams.size());
        //this list contains diagrams who are C-UPDATE choice and all others diagrams to update to the server and who did not create conflict
        toUpdateToSrvD= new ArrayList<>();

        for(ActionDoneResponse actionResponse:actionsDone){
            if(actionResponse.action.equals("CLIENT-CHOICE")){
                DialogWindowHelper.getInstance().showConfirm(actContext,"Conflict for"+diagrams.get(indexList).title,"Do you wish to keep the Android version?",this);
            }
            else if(actionResponse.action.equals("CREATED")){
                diagrams.get(indexList).serverId=actionResponse.serverId;
                diagramDao.update(diagrams.get(indexList));
            }
            else if(actionResponse.action.equals("UPDATE")){
                 Diagram d=diagrams.get(indexList);
                if(d.serverId==-1){
                    d.serverId=actionResponse.serverId;
                    diagrams.set(indexList,d);
                    diagramDao.update(d);
                }
                toUpdateToSrvD.add(d);
                d=null;
            }
            else if (actionResponse.action.equals("DELETE")){
                diagrams.get(indexList).id=-1;
                diagramDao.deleteById(diagrams.get(indexList).id);
            }
            indexList++;
        }
        choicePerformer = new ChoicePerformer(userChoices);
        try {
            choicePerformer.perform();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sUpdateD=choicePerformer.sUpdateD;
        toUpdateToSrvD.addAll(choicePerformer.cUpdateD);
        choicePerformer=null;
        pushUserChoices();
    }

    //------CALLBACK FOR USERCHOICE-------------
    //He wish to keep Android version of the diagram
    @Override
    public void accepted() {
        String choice =actionsDone.get(indexList).choices;
        choice=choice.split("|")[0];
        userChoices.put(diagrams.get(indexList).id,choice);
    }

    //He wish to keep Server version of the diagram
    @Override
    public void canceled() {
        String choice =actionsDone.get(indexList).choices;
        choice=choice.split("|")[1];
        userChoices.put(diagrams.get(indexList).id,choice);
    }
    //--------END CALLBACK FOR USERCHOCIE------------

    private void pushUserChoices(){
        pushUCAT = new PushUserChoiceAsynchTask(this,userChoices);
        pushUCAT.execute();
    }

    public void pushUserChoiceExecuted(){
        pushUCAT=null;
        sUpdateAT=new SUpdateAsynchTask(this,sUpdateD);
        sUpdateAT.execute();
        sUpdateD=null;

    }

    public void sUpdateExecuted(){
        sUpdateAT=null;
        cUpDelToSrvAT= new CUpdateAsynchTask(this,toUpdateToSrvD);
        cUpDelToSrvAT.execute();
    }

    public void cUpdateExecuted(){
        cUpDelToSrvAT=null;
        /*
        - pictures with web service
        - need to pull all diagrams created updated or deleted from server who did not create conflict during pushDiagram
         */

        for(Diagram d: diagrams){
            d.needSynch=false;
            //it means the Diagram does not exist in db anymore, see above
            if(d.id==-1)
                break;
            try {
                diagramDao.update(d);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private  void initDBAccess(){

        dbHelper = OpenHelperManager.getHelper(MyApplication.getAppContext(), OrmLiteDatabaseHelper.class);
        diagramDao=dbHelper.getDiagramDao();
    }


}
