package schn.beme.storysummary.mvp.defaults;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
//        MyApplication.setAppContext(this);
    }


    @Override
    protected void onStart(){
        super.onStart();
        /*if(MyApplication.isCrntActivityCxtNull()){
            MyApplication.setAppContext(this);
        }*/
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /*if(MyApplication.isCrntActivityCxtNull()){
            MyApplication.setAppContext(this);
        }*/
    }


    @Override
    protected void onPause()
    {
        super.onPause();
//        MyApplication.setAppContext(null);
    }

    @Override
    protected void onStop(){
        super.onStop();
//        MyApplication.setAppContext(null);
    }


    public void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
