package schn.beme.storysummary.mvp.defaults;

import schn.beme.storysummary.mvp.settings.SettingsActivity;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 11-07-16.
 */
public class DefaultActionBarPresenter<V extends DefaultActionBarPresenter.View> extends Presenter<V> {


    public DefaultActionBarPresenter(V view) {

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
        IntentHelper.getInstance().startActivityNoFlags(SettingsActivity.class);
    }

    public interface View extends Presenter.View
    {

    }
}
