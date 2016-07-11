package schn.beme.storysummary.presenterhelper;

import android.content.Intent;

import schn.beme.storysummary.DefaultActionBarActivity;
import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.diagram.DiagramActivity;
import schn.beme.storysummary.registration.RegistrationActivity;
import schn.beme.storysummary.sectionchoice.SectionChoiceActivity;
import schn.beme.storysummary.settings.SettingsActivity;

/**
 * Created by Dorito on 10-07-16.
 */
public class IntentHelper {



    public void startRegistrationActivity(){
       Intent intent= new Intent(MyApplication.getCurntActivityContext(), RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getCurntActivityContext().startActivity(intent);
    }
    //        startActivity(intent.FLAG_ACTIVITY_CLEAR_TOP); utiliser ça quand le compte est supprimé et revenir au début
//        finish();      //Calls are asych, startActivity() will be called

    public void startSectionChoiceActivity(){
        Intent intent= new Intent(MyApplication.getCurntActivityContext(), SectionChoiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getCurntActivityContext().startActivity(intent);
    }


    public void startSettingsActivity()
    {
        Intent intent= new Intent(MyApplication.getCurntActivityContext(), SettingsActivity.class);
        MyApplication.getCurntActivityContext().startActivity(intent);
    }

    public void startDiagramActivity()
    {
        Intent intent = new Intent(MyApplication.getCurntActivityContext(), DiagramActivity.class);
        MyApplication.getCurntActivityContext().startActivity(intent);
    }


}
