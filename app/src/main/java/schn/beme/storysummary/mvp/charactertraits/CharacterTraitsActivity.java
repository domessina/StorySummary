package schn.beme.storysummary.mvp.charactertraits;

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

import schn.beme.be.storysummary.BuildConfig;
import schn.beme.be.storysummary.R;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarActivity;
import schn.beme.storysummary.narrativecomponent.Character;

/**
 * Created by Dorito on 07-09-16.
 */
public class CharacterTraitsActivity extends DefaultActionBarActivity
    implements CharacterTraitsPresenter.View
{

    private Character character;
    protected CharacterTraitsPresenter<CharacterTraitsPresenter.View> presenter;
    private RecyclerView recyclerV;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_character_traits;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getIntentData();
        presenter=new CharacterTraitsPresenter<CharacterTraitsPresenter.View>(this,character.id);
        initContent();
        setTitle(R.string.title_activity_char_trait);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        presenter.onStop();
    }

    private void initContent() {

        character=presenter.getCharacter(character.id);
        EditText charNameEdit = (EditText) findViewById(R.id.edit_char_trait_name);
        charNameEdit.setText(character.name, TextView.BufferType.EDITABLE);
        EditText charTypeEdit = (EditText) findViewById(R.id.edit_char_trait_type);
        charTypeEdit.setText(character.type, TextView.BufferType.EDITABLE);
        EditText charNoteEdit = (EditText) findViewById(R.id.edit_char_trait_note);
        charNoteEdit.setText(character.note, TextView.BufferType.EDITABLE);

        initRecyclerView();
        initFAB();
    }

    private void getIntentData()
    {
        character=new Character();
        character.id = getIntent().getIntExtra("characterId",-1);
        if(BuildConfig.DEBUG&&!(character.id>-1))
            throw new AssertionError("character id have to be >-1");
    }

    private void initRecyclerView(){
        recyclerV = (RecyclerView) findViewById(R.id.recycler_char_trait);
        recyclerV.setHasFixedSize(true);
        LinearLayoutManager lLManager = new LinearLayoutManager(this);
        lLManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerV.setLayoutManager(lLManager);
        recyclerV.setAdapter(presenter.getTraitAdapter());
        registerForContextMenu(recyclerV);

    }

    private void initFAB() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_scroll);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.addTrait();
            }
        });
    }

    public void onClickSaveBtn(View v){
        presenter.saveBtnClicked();
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

    //-------END MENU-----------
    @Override
    public void scrollToEnd() {

        recyclerV.scrollToPosition(presenter.getLastPositionTotal());
    }

    @Override
    public String getCharacterName(){
        return ((TextView)findViewById(R.id.edit_char_trait_name)).getText().toString();
    }

    @Override
    public String getCharacterType(){
        return ((TextView)findViewById(R.id.edit_char_trait_type)).getText().toString();
    }

    @Override
    public String getCharacterNote(){
        return ((TextView)findViewById(R.id.edit_char_trait_note)).getText().toString();
    }

}
