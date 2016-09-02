package schn.beme.storysummary.synchronization;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogWindowHelper;

/**
 * Created by Dorito on 02-09-16.
 */
public class SynchManager implements ConfirmDialogListener {

    PushDiagramsAsynchTask pushDAT;
    PushUserChoiceAsynchTask pushUCAT;
    List<ActionDoneResponse> actionsDone;
    List<Diagram> diagrams;
    HashMap<Integer,String> userChoices;
    ChoicePerformer choicePerformer;
    int indexList;

    public void synchronise(){
        pushDAT=new PushDiagramsAsynchTask(this);
        pushDAT.execute();
    }

    public void pushDiagramsExecuted(){

        actionsDone=pushDAT.actionsDone;
        diagrams =pushDAT.diagrams;
        pushDAT=null;
        indexList=0;
        userChoices = new HashMap<>(diagrams.size());
        for(ActionDoneResponse actionResponse:actionsDone){
            if(actionResponse.action.equals("CLIENT-CHOICE")){
                DialogWindowHelper.getInstance().showConfirm("Conflict for"+diagrams.get(indexList).title,"Do you wish to keep the Android version?",this);
            }
            indexList++;
        }

        choicePerformer = new ChoicePerformer(userChoices);
        try {
            choicePerformer.perform();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    }


}
