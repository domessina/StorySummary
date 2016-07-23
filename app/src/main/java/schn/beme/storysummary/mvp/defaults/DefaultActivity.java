package schn.beme.storysummary.mvp.defaults;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import schn.beme.storysummary.MyApplication;

/**
 * Created by Dorito on 11-07-16.
 */
public class DefaultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        //needed for DBHelper in presenters before oncreate instantiate a prensenter
        MyApplication.setCrntActivityContext(this);
    }



    @Override
    protected void onResume()
    {
        super.onResume();
        if(MyApplication.isCrntActivityCxtNull()){
            MyApplication.setCrntActivityContext(this);
        }
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        MyApplication.setCrntActivityContext(null);
    }

}
