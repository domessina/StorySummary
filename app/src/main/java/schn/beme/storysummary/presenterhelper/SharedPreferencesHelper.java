package schn.beme.storysummary.presenterhelper;

import android.content.Context;
import android.content.SharedPreferences;

import schn.beme.storysummary.MyApplication;

/**
 * Created by Dorito on 10-07-16.
 */
public class SharedPreferencesHelper {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public SharedPreferencesHelper()
    {
        sharedPref= MyApplication.getCurntActivityContext().getSharedPreferences("infos", Context.MODE_PRIVATE);
        editor= sharedPref.edit();
    }

    public  void setUserRegistered(boolean registered)
    {
        editor.putBoolean("registered",registered);
        editor.apply();  //faster than commit , because does not return boolean and is asynchronous(<>synchronized)
    }

    public boolean isUserRegistered()
    {
       return sharedPref.getBoolean("registered",false);
    }
}
