package schn.beme.storysummary.diagram;

import schn.beme.storysummary.Presenter;
import schn.beme.storysummary.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.presenterhelper.SharedPreferencesHelper;

/**
 * Created by Dorito on 11-07-16.
 */
public class DiagramPresenter extends DefaultActionBarPresenter {


    public DiagramPresenter(View view) {

        super(view);
    }



    public interface View extends DefaultActionBarPresenter.View
    {

    }


}
