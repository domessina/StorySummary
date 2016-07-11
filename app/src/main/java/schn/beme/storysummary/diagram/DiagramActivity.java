package schn.beme.storysummary.diagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.DefaultActionBarActivity;
import schn.beme.storysummary.Presenter;

public class DiagramActivity extends DefaultActionBarActivity implements DiagramPresenter.View{

    private DiagramPresenter presenter;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_diagram;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        presenter=new DiagramPresenter(this);
    }




}

