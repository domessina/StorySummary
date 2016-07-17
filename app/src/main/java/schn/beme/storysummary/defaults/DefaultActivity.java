package schn.beme.storysummary.defaults;

import android.support.v7.app.AppCompatActivity;

import schn.beme.storysummary.MyApplication;

/**
 * Created by Dorito on 11-07-16.
 */
public class DefaultActivity extends AppCompatActivity {


    @Override
    protected void onResume()
    {
        super.onResume();
        MyApplication.setCurntActivityContext(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MyApplication.setCurntActivityContext(null);
    }

}
