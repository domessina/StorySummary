package schn.beme.storysummary.mvp.diagram;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


import schn.beme.be.storysummary.R;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarActivity;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;

public class DiagramActivity extends DefaultActionBarActivity implements DefaultActionBarPresenter.View, DiagramPresenter.View {

    private DiagramPresenter presenter;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_diagram;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter = new DiagramPresenter(this);

        initContent();
    }

    @Override
    protected void onStart() {

        super.onStart();
        presenter.onStart();
    }

    private void initContent() {

        initRecycleView();
    }

    private void initRecycleView() {

        RecyclerView recyclerD = (RecyclerView) findViewById(R.id.recyclerDiagram);
        recyclerD.setHasFixedSize(true);
        LinearLayoutManager lLManager = new LinearLayoutManager(this);
        lLManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerD.setLayoutManager(lLManager);
        recyclerD.setAdapter(presenter.getDiagramAdapter());
        registerForContextMenu(recyclerD);


    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    //----------------MENUS-------------------
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_default_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.context_default_delete:
                presenter.contextMenuDelete();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

}

