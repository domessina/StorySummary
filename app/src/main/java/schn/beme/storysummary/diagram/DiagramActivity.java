package schn.beme.storysummary.diagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.Presenter;

public class DiagramActivity extends AppCompatActivity implements DiagramPresenter.View{

    private DiagramPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);
        presenter=new DiagramPresenter(this);
        initViews();
    }

    private void initViews()
    {
        try {
            initToolBar();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    private void initToolBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //may produce nullPointerexception
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(DiagramActivity.this, "settings", Toast.LENGTH_SHORT).show();
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_synchronize:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast.makeText(DiagramActivity.this, "synchronize", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_debug_reg:
                presenter.registerUser();

                return true;

            case R.id.action_debug_unreg:
                presenter.unregisterUser();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}

