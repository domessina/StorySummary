package schn.beme.storysummary.mvp.scene;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Set;

import schn.beme.be.storysummary.BuildConfig;
import schn.beme.be.storysummary.R;
import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarActivity;

public class SceneActivity extends DefaultActionBarActivity implements ScenePresenter.View{

    public int chapterId;
    public String chapterTitle;
    public String chapterNote;
    protected ScenePresenter<SceneActivity> presenter;
    private RecyclerView recyclerV;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_scene;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getIntentData();
        presenter=new ScenePresenter<>(this,chapterId);
        initContent();
        setTitle(R.string.title_activity_scene);
    }

    @Override
    protected void onStart(){
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
    protected void onStop(){
        super.onStop();
        presenter.onPause();
    }

    private void getIntentData(){
        chapterId = getIntent().getIntExtra("chapterId",-1);
        chapterTitle = getIntent().getStringExtra("chapterTitle");
        chapterNote = getIntent().getStringExtra("chapterNote");
        if(BuildConfig.DEBUG&&!(chapterId>-1))
            throw new AssertionError("chapterId have to be >-1");
    }

    private void initContent(){
        EditText editT = (EditText) findViewById(R.id.edit_chapter_title);
        editT.setText(chapterTitle, TextView.BufferType.EDITABLE);

        editT=(EditText) findViewById(R.id.edit_chapter_note);
        editT.setText(chapterNote, TextView.BufferType.EDITABLE);

        initRecyclerView();
        initFAB();
    }

    private void initRecyclerView(){
        recyclerV = (RecyclerView) findViewById(R.id.recycler_scene);
        recyclerV.setHasFixedSize(true);
        GridLayoutManager gLManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        recyclerV.setLayoutManager(gLManager);
        recyclerV.setAdapter(presenter.getSceneAdapter());
        registerForContextMenu(recyclerV);
    }

    private void initFAB() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_scroll);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.addScene();
            }
        });
    }

    //--------------MENUS CONTEXT--------------------
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
    //-------------------END MENU CONTEXT----------------

    public void onClickSaveBtn(View v){
        presenter.saveBtnClicked();
//        performFileSearch();
        /*ImageView img= (ImageView)findViewById(R.id.image_test);
        File d= new File("/"+Environment.DIRECTORY_PICTURES+"/city48.jpg");
        showToast(String.valueOf(d.exists()));
        */
    }


    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, 42);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

//        ImageView img= (ImageView)findViewById(R.id.image_test);
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == 42 && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("pute", "Uri: " + uri.toString());
                showToast(uri.toString());
//                Picasso.with(this)
//                        .load(uri).into(img);
            }
        }
    }

    //----------------INTERFACE VIEW IMPLEMENTATIONS---------------



    @Override
    public String getChapterTitle(){
        return ((TextView)findViewById(R.id.edit_chapter_title)).getText().toString();
    }

    @Override
    public String getChapterNote(){
        return ((TextView)findViewById(R.id.edit_chapter_note)).getText().toString();
    }

    @Override
    public void scrollToEnd() {
        recyclerV.scrollToPosition(presenter.getLastPositionTotal());
    }
}
