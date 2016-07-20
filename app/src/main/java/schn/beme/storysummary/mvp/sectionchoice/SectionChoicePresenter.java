package schn.beme.storysummary.mvp.sectionchoice;


import schn.beme.storysummary.mvp.defaults.Presenter;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 10-07-16.
 */
public class SectionChoicePresenter extends Presenter {



    public void viewInitialized()
    {

        if(!SharedPreferencesHelper.getInstance().isUserRegistered()) {
            IntentHelper.getInstance().startRegistrationActivity();
        }
    }

    public void sectionDiagramSelected()
    {
        IntentHelper.getInstance().startDiagramActivity();
    }


    public interface View extends Presenter.View{

    }
}
