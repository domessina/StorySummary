package schn.beme.storysummary.presenterhelper;

import android.content.Intent;
import android.os.Bundle;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.mvp.registration.RegistrationActivity;
import schn.beme.storysummary.mvp.sectionchoice.SectionChoiceActivity;

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
       intent= new Intent(MyApplication.getCrntActivityContext(), RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getCrntActivityContext().startActivity(intent);
    }
    //        startActivity(intent.FLAG_ACTIVITY_CLEAR_TOP); utiliser ça quand le compte est supprimé et revenir au début
//        finish();      //Calls are asych, startActivity() will be called

    public void startSectionChoiceActivity(){
        intent= new Intent(MyApplication.getCrntActivityContext(), SectionChoiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getCrntActivityContext().startActivity(intent);
    }


    public void startActivityNoFlags(Class<?> cls)
    {
        intent= new Intent(MyApplication.getCrntActivityContext(), cls);
        MyApplication.getCrntActivityContext().startActivity(intent);
    }

    public void startChapterActivity(Class<?> cls, int diagramId, String diagramTitle)
    {
        intent= new Intent(MyApplication.getCrntActivityContext(), cls);
        Bundle bundle=new Bundle();
        bundle.putInt("diagramId",diagramId);
        bundle.putString("diagramTitle",diagramTitle);
        intent.putExtras(bundle);
        MyApplication.getCrntActivityContext().startActivity(intent);
    }





}
