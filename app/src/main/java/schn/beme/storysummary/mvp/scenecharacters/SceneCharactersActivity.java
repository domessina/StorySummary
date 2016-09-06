package schn.beme.storysummary.mvp.scenecharacters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import schn.beme.be.storysummary.BuildConfig;
import schn.beme.be.storysummary.R;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarActivity;

public class SceneCharactersActivity extends DefaultActionBarActivity implements SceneCharactersPresenter.View{

    public int sceneId=-1;
    public String sceneTitle;
    public String sceneNote;
    protected SceneCharactersPresenter presenter;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_scene_characters;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getIntentData();
        presenter=new SceneCharactersPresenter<>(this,sceneId);
        initContent();

    }

    @Override
    protected void onPause(){
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        presenter.onResume();
    }

    private void getIntentData(){
        sceneId=getIntent().getIntExtra("sceneId",-1);
        sceneTitle=getIntent().getStringExtra("sceneTitle");
        sceneNote=getIntent().getStringExtra("sceneNote");
        if(BuildConfig.DEBUG&&!(sceneId>-1))
            throw new AssertionError("sceneId have to be >-1");
    }

    private void initContent(){

        EditText titleEdit=(EditText)findViewById(R.id.edit_scenec_title);
        titleEdit.setText(sceneTitle);
        EditText noteEdit=(EditText)findViewById(R.id.edit_scenec_note);
        noteEdit.setText(sceneNote);
    }

    public void onClickSaveButton(View v){
        presenter.saveBtnClicked();
    }


    //----------PICTURE IMPORTATION--------------
    public void onClickImportButton(View v){
//        performFileSearchMethod1();
        performFileSearchMethod2();
    }

    public void performFileSearchMethod1() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.addCategory( Intent.CATEGORY_APP_GALLERY);
//        intent.addCategory(Intent.ACTION_PICK);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, 42);


    }

    public void performFileSearchMethod2(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"),42);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {


        ImageView img= (ImageView)findViewById(R.id.image_senec_import);
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
                TextView tv=(TextView) findViewById(R.id.text_scenec_picture_uri);
                tv.setText(uri.toString());
                showToast(uri.getPath());
                Bitmap mImageBitmap = null;
                try {
                    mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                img.setImageBitmap(mImageBitmap);
//                img.setImageURI(Uri.parse(new File(uri.getPath()).toString()));
//                Picasso.with(this).load(uri).into(img);

            }
        }
    }

    //-----------END PICTURE IMPORTATION---------------


    @Override
    public String getSceneTitle() {

        return ((TextView)findViewById(R.id.edit_scenec_title)).getText().toString();
    }

    @Override
    public String getSceneNote() {

        return ((TextView)findViewById(R.id.edit_scenec_note)).getText().toString();
    }
}
