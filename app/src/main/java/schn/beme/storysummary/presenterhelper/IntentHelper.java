package schn.beme.storysummary.presenterhelper;

import android.content.Intent;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.diagram.DiagramActivity;
import schn.beme.storysummary.registration.RegistrationActivity;

/**
 * Created by Dorito on 10-07-16.
 */
public class IntentHelper {

    Intent intent;

    public IntentHelper(){


    }

    public void startRegistrationActivity(){
        intent= new Intent(MyApplication.getAppContext(), RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
        MyApplication.getAppContext().startActivity(intent);
    }
}
