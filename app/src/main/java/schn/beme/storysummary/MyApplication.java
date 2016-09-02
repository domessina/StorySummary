package schn.beme.storysummary;

import android.app.Application;
import android.content.Context;

/**
 * Created by Dorito on 10-07-16.
 */
public class MyApplication extends Application {


    private static Context context;
    public static int diagramToRefreshId=-1;
    public static int chapterToRefreshId=-1;
    public static int workingDiagramId;
    public static int userId=-1;

    @Override
    public void onCreate() {
        super.onCreate();
        //it's a better idea to keep it in a variable if I use Activity context
        //here I don't but in case I change my mind, the var context will still be here
        //http://stackoverflow.com/questions/7298731/when-to-call-activity-context-or-application-context
        MyApplication.context = getApplicationContext();
        setConstraintDB();
    }

    private void setConstraintDB()
    {
//        SQLiteDatabase sdldb=new SQLiteDatabase();
    }

    /*
    Si quelque chose ne va pas avec le contexte essaye d'utiliser celiu des activité comme avant
    public static synchronized void setActivityContext(Context context)
    {
        MyApplication.context=context;
    }*/

    public static synchronized Context getAppContext() {

        if(context!=null) {
            return context;
        } else {
            throw new NullPointerException("MyApplication.Context is null");
        }
    }

    public static synchronized boolean isAppContextNull()
    {
        return context==null;
    }




}
