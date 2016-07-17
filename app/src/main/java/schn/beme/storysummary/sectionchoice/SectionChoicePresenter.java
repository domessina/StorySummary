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


    public SectionChoicePresenter(View view)
    {
        super(view);
    }

    public void viewInitialized()
    {

        if(!SharedPreferencesHelper.getInstance().isUserRegistered()) {
            IntentHelper.getInstance().startRegistrationActivity();
        }
    }

    public void sectionDiagramSelected()
    {
        IntentHelper.getInstance().startDiagramActivity();
    }


    public interface View extends Presenter.View{

    }
}
