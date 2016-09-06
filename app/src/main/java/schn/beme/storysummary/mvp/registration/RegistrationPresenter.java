package schn.beme.storysummary.mvp.registration;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.mvp.defaults.DefaultPresenter;
import schn.beme.storysummary.presenterhelper.android.ActivityStarterHelper;
import schn.beme.storysummary.presenterhelper.data.SharedPreferencesHelper;

/**
 * Created by Dorito on 09-07-16.
 */
public class RegistrationPresenter<V extends RegistrationPresenter.View> extends DefaultPresenter {


    public RegistrationPresenter(V v){
        super(v);
    }

    public void fbBtnClicked()
    {
        SharedPreferencesHelper.getInstance().setUserRegistered(true, 1);
//        ActivityStarterHelper.getInstance().startSectionChoiceActivity();
        getView().startSectionChoiceActivity();
    }

    public interface View extends DefaultPresenter.View
    {

    }





}
