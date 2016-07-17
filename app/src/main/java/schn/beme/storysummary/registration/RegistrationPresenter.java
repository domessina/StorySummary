package schn.beme.storysummary.registration;

import android.content.Intent;

import java.lang.ref.WeakReference;

import schn.beme.storysummary.Presenter;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 09-07-16.
 */
public class RegistrationPresenter extends Presenter {


    public RegistrationPresenter(View view)
    {
        super(view);
    }

    public void fbBtnClicked()
    {
        SharedPreferencesHelper.getInstance().setUserRegistered(true);
        IntentHelper.getInstance().startSectionChoiceActivity();
    }

    public interface View extends Presenter.View
    {

    }





}
