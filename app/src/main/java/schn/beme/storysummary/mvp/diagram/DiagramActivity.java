package schn.beme.storysummary.mvp.diagram;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import schn.beme.be.storysummary.R;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarActivity;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;

public class DiagramActivity extends DefaultActionBarActivity implements DefaultActionBarPresenter.View, DiagramPresenter.View{

    private DiagramPresenter presenter;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_diagram;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter=new DiagramPresenter(this);
        initContent();
    }

    private void initContent()
    {
        initRecycleView();
    }

    private void initRecycleView()
    {
        RecyclerView recyclerD = (RecyclerView) findViewById(R.id.recyclerDiagram);
        recyclerD.setHasFixedSize(true);
        LinearLayoutManager lLManager = new LinearLayoutManager(this);
        lLManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerD.setLayoutManager(lLManager);

        DiagramAdapter ca = new DiagramAdapter(presenter.createList(30));
        recyclerD.setAdapter(ca);
    }



    @Override
    protected void onResume() {

        super.onResume();
        presenter = new DiagramPresenter(this);
    }





}

