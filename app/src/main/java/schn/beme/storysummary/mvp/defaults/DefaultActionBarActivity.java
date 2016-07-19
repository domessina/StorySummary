package schn.beme.storysummary.mvp.defaults;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import schn.beme.be.storysummary.R;

/**
 * Created by Dorito on 11-07-16.
 */
public abstract class DefaultActionBarActivity extends DefaultActivity implements DefaultActionBarPresenter.View {

    private DefaultActionBarPresenter barPresenter;


    /**
     * Don't use setContentView from subclasses of DefaultActionBarActivity
     * getLayoutId is the only way
     * */
    protected abstract @LayoutRes int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //contentView forced to be set here, and not in the inherited activity of this class. It crashes otherwise
        setContentView(getLayoutId());
        initToolBar();
    }


    private void initToolBar()
    {
        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //may produce nullPointerexception
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        barPresenter =new DefaultActionBarPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                barPresenter.actionSettings();
                return true;

            case R.id.action_synchronize:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.action_debug_reg:
                barPresenter.registerUser();

                return true;

            case R.id.action_debug_unreg:
                barPresenter.unregisterUser();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
