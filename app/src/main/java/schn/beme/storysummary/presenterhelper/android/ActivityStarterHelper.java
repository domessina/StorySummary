package schn.beme.storysummary.presenterhelper.android;

import android.content.Intent;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.mvp.chapter.ChapterActivity;
import schn.beme.storysummary.mvp.registration.RegistrationActivity;
import schn.beme.storysummary.mvp.scene.SceneActivity;
import schn.beme.storysummary.mvp.scenecharacters.SceneCharactersActivity;
import schn.beme.storysummary.mvp.sectionchoice.SectionChoiceActivity;
import schn.beme.storysummary.presenterhelper.Helper;

/**
 * Created by Dorito on 10-07-16.
 */
public class ActivityStarterHelper implements Helper.ActivityStarter {

    private final Intent intent=new Intent();


    //-----------SINGLETON HOLDER METHODOLOGY---------------

    private ActivityStarterHelper(){}

    public static ActivityStarterHelper getInstance()
    {
        return ActivityStarterHolder.instance;
    }

    private static class ActivityStarterHolder
    {
        private final static ActivityStarterHelper instance = new ActivityStarterHelper();
    }




    //----------------WORKING METHODS---------------

    @Override
    public void startRegistrationActivity(){
        intent.setClass(MyApplication.getAppContext(),RegistrationActivity.class);
                //if activities can't be started any more, uncomment theses lines and remove the one above.
                // intent= new Intent(MyApplication.getAppContext(),RegistrationActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getAppContext().startActivity(intent);
    }
    //        startActivity(intent.FLAG_ACTIVITY_CLEAR_TOP); utiliser ça quand le compte est supprimé et revenir au début
//        finish();      //Calls are asych, startActivity() will be called

    @Override
    public void startSectionChoiceActivity(){
        intent.setClass(MyApplication.getAppContext(),SectionChoiceActivity.class);
//        intent= new Intent(MyApplication.getAppContext(), SectionChoiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getAppContext().startActivity(intent);
    }


    @Override
    public void startActivityNoFlags(Class<?> cls) {
        intent.setClass(MyApplication.getAppContext(),cls);
//        intent= new Intent(MyApplication.getAppContext(), cls);
        MyApplication.getAppContext().startActivity(intent);
    }

    @Override
    public void startChapterActivity(int diagramId, String diagramTitle) {
        intent.setClass(MyApplication.getAppContext(),ChapterActivity.class);
//        intent= new Intent(MyApplication.getAppContext(), ChapterActivity.class);
        intent.putExtra("diagramId",diagramId);
        intent.putExtra("diagramTitle",diagramTitle);
        MyApplication.getAppContext().startActivity(intent);
    }

    @Override
    public void startSceneActivity(int chapterId, String chapterTitle, String chapterNote) {
        intent.setClass(MyApplication.getAppContext(),SceneActivity.class);
//        intent= new Intent(MyApplication.getAppContext(), SceneActivity.class);
        intent.putExtra("chapterId",chapterId);
        intent.putExtra("chapterNote",chapterNote);
        intent.putExtra("chapterTitle",chapterTitle);
        MyApplication.getAppContext().startActivity(intent);
    }

    @Override
    public void startSceneCharactersActivity(int sceneId, String sceneTitle, String sceneNote){
        intent.setClass(MyApplication.getAppContext(),SceneCharactersActivity.class);
//        intent=new Intent(MyApplication.getAppContext(),SceneCharactersActivity.class);
        intent.putExtra("sceneId",sceneId);
        intent.putExtra("sceneTitle",sceneTitle);
        intent.putExtra("sceneNote",sceneNote);

        //there is a unknown bug, the activity is launched multiple times! In consequence I do the following action:
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        MyApplication.getAppContext().startActivity(intent);
    }

    //---------------END WORKING METHODS----------------






}
