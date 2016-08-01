package schn.beme.storysummary;

import android.app.Application;
import android.content.Context;

import schn.beme.storysummary.presenterhelper.DatabaseHelper;

/**
 * Created by Dorito on 10-07-16.
 */
public class MyApplication extends Application {


    private static Context context;
    public static int diagramToRefreshId=-1;
    public static int chapterToRefreshId=-1;

    @Override
    public void onCreate() {
        super.onCreate();
//        MyApplication.context = getApplicationContext();
        setConstraintDB();
    }

    private void setConstraintDB()
    {
//        SQLiteDatabase sdldb=new SQLiteDatabase();
    }

    public static synchronized void setCrntActivityContext(Context context)
    {
        MyApplication.context=context;
    }

    public static synchronized Context getCrntActivityContext() {

        if(context!=null) {
            return context;
        } else {
            throw new NullPointerException("MyApplication.Context is null");
        }
    }

    public static synchronized boolean isCrntActivityCxtNull()
    {
        return context==null;
    }




}
