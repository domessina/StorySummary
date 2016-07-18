package schn.beme.storysummary.mvp.registration;

import schn.beme.storysummary.mvp.Presenter;
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