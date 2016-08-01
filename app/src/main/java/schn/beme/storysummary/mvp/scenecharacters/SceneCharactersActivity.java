package schn.beme.storysummary.mvp.scenecharacters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarActivity;

public class SceneCharactersActivity extends DefaultActionBarActivity {

    @Override
    protected int getLayoutId() {

        return R.layout.activity_scene_characters;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_characters);
    }

    public void onClickSaveButton(View v){

    }


    //----------PICTURE IMPORTATION--------------
    public void onClickImportButton(View v){
        performFileSearch();
    }

    public void performFileSearch() {

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

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {


//        ImageView img= (ImageView)findViewById(R.id.image_scenec_import);
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
                TextView tv=(TextView) findViewById(R.id.text_scenec_picture_uri);
                tv.setText(uri.toString());
 /*               ImageView img=new ImageView(this);
                Picasso.with(this).load(uri).into(img);*/

            }
        }
    }

    //-----------END PICTURE IMPORTATION---------------
}
