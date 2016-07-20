package schn.beme.storysummary.mvp.defaults;

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
     * If the prensenter does not need to call methods of the view
     * */
    public Presenter(){}

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

  /*  private static boolean isXLargeTablet(Context context) {

        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }*/

}
