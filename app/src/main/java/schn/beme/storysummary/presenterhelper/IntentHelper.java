package schn.beme.storysummary.presenterhelper;

import android.content.Intent;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.diagram.DiagramActivity;
import schn.beme.storysummary.registration.RegistrationActivity;
import schn.beme.storysummary.sectionchoice.SectionChoiceActivity;

/**
 * Created by Dorito on 10-07-16.
 */
public class IntentHelper {

    Intent intent;

    public IntentHelper(){


    }

    public void startRegistrationActivity(){
        intent= new Intent(MyApplication.getAppContext(), RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getAppContext().startActivity(intent);
    }
    //        startActivity(intent.FLAG_ACTIVITY_CLEAR_TOP); utiliser ça quand le compte est supprimé et revenir au début
//        finish();      //Calls are asych, startActivity() will be called

    public void startSectionChoiceActivity(){
        intent= new Intent(MyApplication.getAppContext(), SectionChoiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getAppContext().startActivity(intent);
    }

}
