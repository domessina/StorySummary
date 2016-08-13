package schn.beme.storysummary.mvp.sectionchoice;


import schn.beme.storysummary.mvp.defaults.DefaultPresenter;
import schn.beme.storysummary.mvp.diagram.DiagramActivity;
import schn.beme.storysummary.presenterhelper.android.ActivityStarterHelper;
import schn.beme.storysummary.presenterhelper.data.SharedPreferencesHelper;

/**
 * Created by Dorito on 10-07-16.
 */
public class SectionChoicePresenter extends DefaultPresenter {



    public void viewInitialized()
    {

        if(!SharedPreferencesHelper.getInstance().isUserRegistered()) {
            ActivityStarterHelper.getInstance().startRegistrationActivity();
        }
    }

    public void sectionDiagramSelected()
    {
        ActivityStarterHelper.getInstance().startActivityNoFlags(DiagramActivity.class);
    }


    public interface View extends DefaultPresenter.View{

    }
}
