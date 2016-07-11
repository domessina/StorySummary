package schn.beme.storysummary.sectionchoice;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import schn.beme.storysummary.Presenter;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 10-07-16.
 */
public class SectionChoicePresenter extends Presenter {

    private SharedPreferencesHelper sharedPrefHelper;
    private IntentHelper intentHelper;

    public SectionChoicePresenter(View view)
    {
        super(view);
        sharedPrefHelper=new SharedPreferencesHelper();
        intentHelper=new IntentHelper();
    }

    public void viewInitialized()
    {

        if(!sharedPrefHelper.isUserRegistered()) {
            intentHelper.startRegistrationActivity();
        }
    }

    public void sectionDiagramSelected()
    {
        intentHelper.startDiagramActivity();
    }


    public interface View extends Presenter.View{

    }
}
