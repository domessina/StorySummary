package schn.beme.storysummary.mvp.diagram;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import schn.beme.be.storysummary.R;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarActivity;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;

public class DiagramActivity extends DefaultActionBarActivity implements DefaultActionBarPresenter.View, DiagramPresenter.View {

    protected DiagramPresenter presenter;
    private RecyclerView recyclerV;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_diagram;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter = new DiagramPresenter<DiagramPresenter.View>(this);
        initContent();
        setTitle(R.string.title_activity_diagram);
    }

    @Override
    protected void onStart() {

        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    private void initContent() {

        initRecycleView();
        initFAB();
    }

    private void initRecycleView() {

        recyclerV = (RecyclerView) findViewById(R.id.recycler_diagram);
        recyclerV.setHasFixedSize(true);
        LinearLayoutManager lLManager = new LinearLayoutManager(this);
        lLManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerV.setLayoutManager(lLManager);
        recyclerV.setAdapter(presenter.getDiagramAdapter());
        registerForContextMenu(recyclerV);


    }

    private void initFAB() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_scroll);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.addDiagram();
            }
        });
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

    //----------INTERFACE VIEW -----------------


    @Override
    public void scrollToEnd() {

        recyclerV.scrollToPosition(presenter.getLastPositionTotal());
    }
}

