package schn.beme.storysummary;

import java.lang.ref.WeakReference;

/**
 * Created by Dorito on 11-07-16.
 */
public class Presenter {

    private WeakReference<View> view;

    public Presenter(View view)
    {
        this.view=new WeakReference<>(view);
    }

    /**
     * use it carefully to prevent memory leak
     * with the activity context returned, in the case
     * of the view is an activity
     * @return the view
     */
    public View getView()
    {
        if ( view != null )
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View{


    }


}
