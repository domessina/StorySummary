package schn.beme.storysummary.registration;

import java.lang.ref.WeakReference;

import schn.beme.storysummary.Presenter;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 09-07-16.
 */
public class RegistrationPresenter extends Presenter {

    IntentHelper intentHelper;
    SharedPreferencesHelper sharedPrefHelper;

    public RegistrationPresenter(View view)
    {
        super(view);
        intentHelper=new IntentHelper();
        sharedPrefHelper = new SharedPreferencesHelper();
    }

    public void fbBtnClicked()
    {
        sharedPrefHelper.setUserRegistered(true);
        intentHelper.startSectionChoiceActivity();
    }

    public interface View extends Presenter.View
    {

    }





}
