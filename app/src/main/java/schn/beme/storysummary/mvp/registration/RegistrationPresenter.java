package schn.beme.storysummary.mvp.registration;

import schn.beme.storysummary.mvp.defaults.Presenter;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 09-07-16.
 */
public class RegistrationPresenter extends Presenter {



    public void fbBtnClicked()
    {
        SharedPreferencesHelper.getInstance().setUserRegistered(true);
        IntentHelper.getInstance().startSectionChoiceActivity();
    }

    public interface View extends Presenter.View
    {

    }





}
