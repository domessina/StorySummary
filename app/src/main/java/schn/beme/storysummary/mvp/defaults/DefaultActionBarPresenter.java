package schn.beme.storysummary.mvp.defaults;

import schn.beme.storysummary.mvp.Presenter;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 11-07-16.
 */
public class DefaultActionBarPresenter extends Presenter {


    public DefaultActionBarPresenter(View view) {

        super(view);
    }

    public void registerUser()
    {
        SharedPreferencesHelper.getInstance().setUserRegistered(true);
    }

    public void unregisterUser()
    {
        SharedPreferencesHelper.getInstance().setUserRegistered(false);
    }

    public void actionSettings()
    {
        IntentHelper.getInstance().startSettingsActivity();
    }

    public interface View extends Presenter.View
    {

    }
}
