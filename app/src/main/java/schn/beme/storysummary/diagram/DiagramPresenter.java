package schn.beme.storysummary.diagram;

import schn.beme.storysummary.Presenter;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 11-07-16.
 */
public class DiagramPresenter extends Presenter {

    SharedPreferencesHelper sharedPrefHelper;

    public DiagramPresenter(View view) {

        super(view);
        sharedPrefHelper=new SharedPreferencesHelper();
    }

    public void registerUser()
    {
        sharedPrefHelper.setUserRegistered(true);
    }

    public void unregisterUser()
    {
       sharedPrefHelper.setUserRegistered(false);
    }

    public interface View extends Presenter.View
    {

    }


}
