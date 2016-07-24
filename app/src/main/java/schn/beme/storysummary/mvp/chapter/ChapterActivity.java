package schn.beme.storysummary.mvp.chapter;

import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.MyApplication;
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
        getIntentData();
        presenter=new ChapterPresenter<ChapterPresenter.View>(this,diagramId);

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

                presenter.addChapter();
            }
        });
    }

    public void onClickSaveBtn(View v){
        presenter.saveBtnClicked();
    }

    public void onClickUpDownBtn(View v){

        boolean isDownBtn=(v.getId()==R.id.card_chapter_down);
        presenter.downUpClicked(isDownBtn,(Integer)v.getTag());

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

    @Override
    public String getDiagramTitle(){
        return ((TextView)findViewById(R.id.edit_diagram_title)).getText().toString();
    }

    @Override
    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
