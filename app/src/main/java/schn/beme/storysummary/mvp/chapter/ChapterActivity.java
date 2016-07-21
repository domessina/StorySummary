package schn.beme.storysummary.mvp.chapter;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarActivity;

public class ChapterActivity extends DefaultActionBarActivity implements ChapterPresenter.View{

    private ChapterPresenter presenter;
    private RecyclerView recyclerV;
    public int diagramId=-1;
    public String diagramTitle;
    Set<String> s;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_chapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter=new ChapterPresenter(this);

       initContent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private void initContent() {

        getIntentData();

        EditText diagramTitleTv = (EditText) findViewById(R.id.edit_diagram_title);
        diagramTitleTv.setText(diagramTitle, TextView.BufferType.EDITABLE);

        initRecyclerView();
        initFAB();
    }

    private void getIntentData()
    {
        s=getIntent().getExtras().keySet();
        diagramId = getIntent().getExtras().getInt("diagramId");
        diagramTitle = getIntent().getExtras().getString("diagramTitle");
    }

    private void initRecyclerView(){
        recyclerV = (RecyclerView) findViewById(R.id.recycler_chapter);
        recyclerV.setHasFixedSize(true);
        LinearLayoutManager lLManager = new LinearLayoutManager(this);
        lLManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerV.setLayoutManager(lLManager);
        recyclerV.setAdapter(presenter.getChapterAdapter());
        registerForContextMenu(recyclerV);

    }

    private void initFAB() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_scroll);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.newChapter();
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


    @Override
    public void scrollToEnd() {

        recyclerV.scrollToPosition(presenter.getLastPositionTotal());
    }

}
