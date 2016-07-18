package schn.beme.storysummary.presenterhelper;

import android.content.Intent;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.mvp.diagram.DiagramActivity;
import schn.beme.storysummary.mvp.registration.RegistrationActivity;
import schn.beme.storysummary.mvp.sectionchoice.SectionChoiceActivity;
import schn.beme.storysummary.mvp.settings.SettingsActivity;

/**
 * Created by Dorito on 10-07-16.
 */
public class IntentHelper {

    private Intent intent;


    //-----------SINGLETON HOLDER METHODOLOGY---------------

    private IntentHelper(){}

    public static IntentHelper getInstance()
    {
        return IntentHelperHolder.instance;
    }

    private static class IntentHelperHolder
    {
        private final static IntentHelper instance = new IntentHelper();
    }




    //----------------WORKING METHODS---------------

    public void startRegistrationActivity(){
       intent= new Intent(MyApplication.getCurntActivityContext(), RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getCurntActivityContext().startActivity(intent);
    }
    //        startActivity(intent.FLAG_ACTIVITY_CLEAR_TOP); utiliser ça quand le compte est supprimé et revenir au début
//        finish();      //Calls are asych, startActivity() will be called

    public void startSectionChoiceActivity(){
        intent= new Intent(MyApplication.getCurntActivityContext(), SectionChoiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getCurntActivityContext().startActivity(intent);
    }


    public void startSettingsActivity()
    {
        intent= new Intent(MyApplication.getCurntActivityContext(), SettingsActivity.class);
        MyApplication.getCurntActivityContext().startActivity(intent);
    }

    public void startDiagramActivity()
    {
        intent = new Intent(MyApplication.getCurntActivityContext(), DiagramActivity.class);
        MyApplication.getCurntActivityContext().startActivity(intent);
    }


}
