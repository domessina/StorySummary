package schn.beme.storysummary.presenterhelper;

import android.graphics.drawable.Drawable;

import com.j256.ormlite.dao.Dao;

import schn.beme.storysummary.synchronization.ActionDoneResponse;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.narrativecomponent.E_NarrativeComponent;
import schn.beme.storysummary.narrativecomponent.NarrativeComponent;
import schn.beme.storysummary.narrativecomponent.Scene;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmDialogListener;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditDialogListener;

/**
 * Created by Dorito on 06-08-16.
 */
public interface Helper {

    interface ActivityStarter{
        void startRegistrationActivity();
        void startSectionChoiceActivity();
        void startActivityNoFlags(Class<?> cls);
        void startChapterActivity(int diagramId, String diagramTitle);
        void startSceneActivity(int chapterId, String chapterTitle, String chapterNote);
        void startSceneCharactersActivity(int sceneId, String sceneTitle, String sceneNote);
    }

    interface DialogWindow{
        void showConfirm(String title, String msg, final ConfirmDialogListener listener);
        void showConfirmEditText(String title, String msg,final boolean empty, final ConfirmEditDialogListener listener);
    }

    interface Database{
        Dao<Diagram, Integer> getDiagramDao() ;
        Dao<Chapter, Integer> getChapterDao()  ;
        Dao<Scene, Integer> getSceneDao()  ;
    }

    interface WebService{

        ActionDoneResponse pushDiagram(Diagram d, String action);
        void pushUserChoice(Diagram d, String action);

        NarrativeComponent getNComponent(int id, E_NarrativeComponent type);
        boolean postNComponent(NarrativeComponent nc, E_NarrativeComponent type);
        boolean putNComponent(NarrativeComponent nc,E_NarrativeComponent type);
        boolean deleteNComponent(NarrativeComponent nc, E_NarrativeComponent type);
        Drawable getPicture(int sceneId);
    }
}
