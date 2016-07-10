package schn.beme.storysummary.registration;

import java.lang.ref.WeakReference;

import schn.beme.storysummary.presenterhelper.IntentHelper;

/**
 * Created by Dorito on 09-07-16.
 */
public class RegistrationPresenter {

    private WeakReference<View> view;

    public RegistrationPresenter(View view)
    {
        this.view=new WeakReference<>(view);
    }



    public interface View
    {

    }



}
