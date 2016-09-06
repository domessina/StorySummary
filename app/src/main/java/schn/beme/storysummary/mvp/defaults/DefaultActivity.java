package schn.beme.storysummary.mvp.defaults;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.mvp.chapter.ChapterActivity;
import schn.beme.storysummary.mvp.registration.RegistrationActivity;
import schn.beme.storysummary.mvp.scene.SceneActivity;
import schn.beme.storysummary.mvp.scenecharacters.SceneCharactersActivity;
import schn.beme.storysummary.mvp.sectionchoice.SectionChoiceActivity;
import schn.beme.storysummary.narrativecomponent.Diagram;

/**
 * Created by Dorito on 11-07-16.
 */
public class DefaultActivity extends AppCompatActivity implements DefaultPresenter.View{

    private Intent intent;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        //needed for DBHelper in presenters before oncreate instantiate a prensenter
//        MyApplication.setAppContext(this);
    }


    @Override
    protected void onStart(){
        super.onStart();
        /*if(MyApplication.isCrntActivityCxtNull()){
            MyApplication.setAppContext(this);
        }*/
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /*if(MyApplication.isCrntActivityCxtNull()){
            MyApplication.setAppContext(this);
        }*/
    }


    @Override
    protected void onPause()
    {
        super.onPause();
//        MyApplication.setAppContext(null);
    }

    @Override
    protected void onStop(){
        super.onStop();
//        MyApplication.setAppContext(null);
    }


    public void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public Context getContext(){return this;}

    @Override
    public void startRegistrationActivity(){
//        intent.setClass(MyApplication.getAppContext(),RegistrationActivity.class);
        //if activities can't be started any more, uncomment theses lines and remove the one above.
         intent= new Intent(this,RegistrationActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    //        startActivity(intent.FLAG_ACTIVITY_CLEAR_TOP); utiliser ça quand le compte est supprimé et revenir au début
//        finish();      //Calls are asych, startActivity() will be called

    @Override
    public void startSectionChoiceActivity(){
//        intent.setClass(MyApplication.getAppContext(),SectionChoiceActivity.class);
        intent= new Intent(this, SectionChoiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public void startActivityNoFlags(Class<?> cls) {
//        intent.setClass(MyApplication.getAppContext(),cls);
        intent= new Intent(this, cls);
       startActivity(intent);
    }

    @Override
    public void startChapterActivity(int diagramId, String diagramTitle) {
//        intent.setClass(MyApplication.getAppContext(),ChapterActivity.class);
        intent= new Intent(this, ChapterActivity.class);
        intent.putExtra("diagramId",diagramId);
        intent.putExtra("diagramTitle",diagramTitle);
        startActivity(intent);
    }

    @Override
    public void startSceneActivity(int chapterId, String chapterTitle, String chapterNote) {
//        intent.setClass(MyApplication.getAppContext(),SceneActivity.class);
        intent= new Intent(this, SceneActivity.class);
        intent.putExtra("chapterId",chapterId);
        intent.putExtra("chapterNote",chapterNote);
        intent.putExtra("chapterTitle",chapterTitle);
        startActivity(intent);
    }

    @Override
    public void startSceneCharactersActivity(int sceneId, String sceneTitle, String sceneNote){
//        intent.setClass(MyApplication.getAppContext(),SceneCharactersActivity.class);
        intent=new Intent(this,SceneCharactersActivity.class);
        intent.putExtra("sceneId",sceneId);
        intent.putExtra("sceneTitle",sceneTitle);
        intent.putExtra("sceneNote",sceneNote);

        //there is a unknown bug, the activity is launched multiple times! In consequence I do the following action:
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }
}
