package schn.beme.storysummary.diagram;

import android.os.Bundle;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.defaults.DefaultActionBarActivity;
import schn.beme.storysummary.defaults.DefaultActionBarPresenter;

public class DiagramActivity extends DefaultActionBarActivity implements DefaultActionBarPresenter.View, DiagramPresenter.View{

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

