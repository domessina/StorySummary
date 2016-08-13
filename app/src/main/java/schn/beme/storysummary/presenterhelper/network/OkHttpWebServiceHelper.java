package schn.beme.storysummary.presenterhelper.network;

import android.graphics.drawable.Drawable;

import schn.beme.storysummary.narrativecomponent.E_NarrativeComponent;
import schn.beme.storysummary.narrativecomponent.NarrativeComponent;
import schn.beme.storysummary.presenterhelper.Helper;

/**
 * Created by Dorito on 13-08-16.
 */
public class OkHttpWebServiceHelper implements Helper.WebService{

    @Override
    public NarrativeComponent getNComponent(int id, E_NarrativeComponent type) {

        return null;
    }

    @Override
    public boolean postNComponent(NarrativeComponent nc, E_NarrativeComponent type) {

        return false;
    }

    @Override
    public boolean putNComponent(NarrativeComponent nc, E_NarrativeComponent type) {

        return false;
    }

    @Override
    public boolean deleteNComponent(NarrativeComponent nc, E_NarrativeComponent type) {

        return false;
    }

    @Override
    public Drawable getPicture(int sceneId) {

        return null;
    }
}
