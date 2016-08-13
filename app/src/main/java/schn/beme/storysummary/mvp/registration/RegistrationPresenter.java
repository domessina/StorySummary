package schn.beme.storysummary.mvp.registration;

import schn.beme.storysummary.mvp.defaults.DefaultPresenter;
import schn.beme.storysummary.presenterhelper.android.ActivityStarterHelper;
import schn.beme.storysummary.presenterhelper.data.SharedPreferencesHelper;

/**
 * Created by Dorito on 09-07-16.
 */
public class RegistrationPresenter extends DefaultPresenter {



    public void fbBtnClicked()
    {
        SharedPreferencesHelper.getInstance().setUserRegistered(true);
        ActivityStarterHelper.getInstance().startSectionChoiceActivity();
    }

    public interface View extends DefaultPresenter.View
    {

    }





}
