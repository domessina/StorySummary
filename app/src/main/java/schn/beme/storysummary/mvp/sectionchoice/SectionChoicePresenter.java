package schn.beme.storysummary.mvp.sectionchoice;


import com.j256.ormlite.android.apptools.OpenHelperManager;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.mvp.defaults.DefaultPresenter;
import schn.beme.storysummary.mvp.diagram.DiagramActivity;
import schn.beme.storysummary.presenterhelper.android.ActivityStarterHelper;
import schn.beme.storysummary.presenterhelper.data.OrmLiteDatabaseHelper;
import schn.beme.storysummary.presenterhelper.data.SharedPreferencesHelper;

/**
 * Created by Dorito on 10-07-16.
 */
public class SectionChoicePresenter<V extends SectionChoicePresenter.View>  extends DefaultPresenter {

    public SectionChoicePresenter(V v){
        super(v);
    }

    public void viewInitialized()
    {
        if(!SharedPreferencesHelper.getInstance().isUserRegistered()) {
//            ActivityStarterHelper.getInstance().startRegistrationActivity();
            getView().startRegistrationActivity();
        }
    }

    public void sectionDiagramSelected()
    {
//        ActivityStarterHelper.getInstance().startActivityNoFlags(DiagramActivity.class);
        getView().startActivityNoFlags(DiagramActivity.class);
    }



    public interface View extends DefaultPresenter.View{

    }
}
