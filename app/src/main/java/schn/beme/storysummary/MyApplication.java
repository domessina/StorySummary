package schn.beme.storysummary;

import android.app.Application;
import android.content.Context;

/**
 * Created by Dorito on 10-07-16.
 */
public class MyApplication extends Application {

    private static Context context;         //TODO hypothese, si tu as un probleme, c'est p-e pcq plusieurs thread essayent d'accéder/modifier en même temps?

    @Override
    public void onCreate() {
        super.onCreate();
//        MyApplication.context = getApplicationContext();
    }

    public static synchronized void setCurntActivityContext(Context context)
    {
        MyApplication.context=context;
    }

    public static synchronized Context getCurntActivityContext() {
        if(context!=null) {
            return context;
        } else {
            throw new NullPointerException("MayApplication.Context is null");
        }
    }


}
