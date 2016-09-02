package schn.beme.storysummary.mvp.defaults;

import schn.beme.storysummary.synchronization.SynchManager;
import schn.beme.storysummary.mvp.settings.SettingsActivity;
import schn.beme.storysummary.presenterhelper.android.ActivityStarterHelper;
import schn.beme.storysummary.presenterhelper.data.SharedPreferencesHelper;

/**
 * Created by Dorito on 11-07-16.
 */
public class DefaultActionBarPresenter<V extends DefaultActionBarPresenter.View> extends DefaultPresenter<V> {


    public DefaultActionBarPresenter(V view) {

        super(view);
    }

    public void registerUser()
    {
        SharedPreferencesHelper.getInstance().setUserRegistered(true,-3);
    }

    public void unregisterUser()
    {   //I set here -3, in setUserRegistered by default -2, and in MyApplication -1
        //In case of problem with userId, we know where it comes from.
        SharedPreferencesHelper.getInstance().setUserRegistered(false,-3);
    }

    public void actionSettings()
    {
        ActivityStarterHelper.getInstance().startActivityNoFlags(SettingsActivity.class);
    }

    public void synchronize(){

        new SynchManager().synchronise();
    }

    public interface View extends DefaultPresenter.View
    {

    }
}
