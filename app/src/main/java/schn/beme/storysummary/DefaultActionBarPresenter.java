package schn.beme.storysummary;

import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 11-07-16.
 */
public class DefaultActionBarPresenter extends Presenter {

    private SharedPreferencesHelper sharedPrefHelper;
    private IntentHelper intentHelper;

    public DefaultActionBarPresenter(View view) {

        super(view);
        sharedPrefHelper=new SharedPreferencesHelper();
        intentHelper= new IntentHelper();
    }

    public void registerUser()
    {
        sharedPrefHelper.setUserRegistered(true);
    }

    public void unregisterUser()
    {
        sharedPrefHelper.setUserRegistered(false);
    }

    public void actionSettings()
    {
        intentHelper.startSettingsActivity();
    }

    public interface View extends Presenter.View
    {

    }
}
