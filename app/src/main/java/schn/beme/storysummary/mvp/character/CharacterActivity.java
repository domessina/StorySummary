package schn.beme.storysummary.mvp.character;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarActivity;

/**
 * Created by Dorito on 07-09-16.
 */
public class CharacterActivity  extends DefaultActionBarActivity implements CharacterPresenter.View{

    protected CharacterPresenter presenter;
    private RecyclerView recyclerV;


    @Override
    protected int getLayoutId() {

        return R.layout.activity_character;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter=new CharacterPresenter<CharacterPresenter.View>(this);
        initContent();
        setTitle(R.string.title_activity_character);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private void initContent() {
        initRecyclerView();
        initFAB();
    }

    private void initRecyclerView(){
        recyclerV = (RecyclerView) findViewById(R.id.recycler_character);
        recyclerV.setHasFixedSize(true);
        LinearLayoutManager lLManager = new LinearLayoutManager(this);
        lLManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerV.setLayoutManager(lLManager);
        recyclerV.setAdapter(presenter.getCharacterAdapter());
        registerForContextMenu(recyclerV);

    }

    private void initFAB() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_scroll);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.addCharacter();
            }
        });
    }

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
