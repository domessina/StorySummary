package schn.beme.storysummary.presenterhelper.data;

import android.content.Context;
import android.content.SharedPreferences;

import schn.beme.storysummary.MyApplication;

/**
 * Created by Dorito on 10-07-16.
 */
public class SharedPreferencesHelper {

    private final SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;



    //-----------SINGLETON HOLDER METHODOLOGY---------------
/**this help us to win many KByte*/

    private SharedPreferencesHelper()
    {
        sharedPref= MyApplication.getAppContext().getSharedPreferences("infos", Context.MODE_PRIVATE);
    }

    public static SharedPreferencesHelper getInstance()
    {
        return SharedPrefHelperHolder.instance;
    }


    private static class SharedPrefHelperHolder
    {
        private final static SharedPreferencesHelper instance = new SharedPreferencesHelper();
    }




    //----------------WORKING METHODS---------------

    public  void setUserRegistered(boolean registered)
    {
        editor= sharedPref.edit();//vaut mieux le faire dans la meme méthode que le commi tou apply
        editor.putBoolean("registered",registered);
        editor.apply();  //faster than commit , because does not return boolean and is asynchronous(<>synchronized)
    }

    public boolean isUserRegistered()
    {
       return sharedPref.getBoolean("registered",false);
    }


}
