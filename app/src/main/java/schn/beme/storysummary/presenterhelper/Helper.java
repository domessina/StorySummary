package schn.beme.storysummary.presenterhelper;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.j256.ormlite.dao.Dao;

import java.util.List;

import schn.beme.storysummary.narrativecomponent.CharacterScene;
import schn.beme.storysummary.narrativecomponent.Trait;
import schn.beme.storysummary.synchronization.ActionDoneResponse;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Character;
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
        void startCharacterTraitsActivity(int characterId);
    }

    interface DialogWindow{
        void showConfirm(Context actContext, String title, String msg, final ConfirmDialogListener listener);
        void showConfirmEditText(Context actContext,String title, String msg,final boolean empty, final ConfirmEditDialogListener listener);
    }

    interface Database{
        Dao<Diagram, Integer> getDiagramDao() ;
        Dao<Chapter, Integer> getChapterDao()  ;
        Dao<Scene, Integer> getSceneDao()  ;
        Dao<Character, Integer> getCharacterDao()  ;
        Dao<Trait, Integer> getTraitDao()  ;
        Dao<CharacterScene, Void> getCharacterSceneDao()  ;
    }

    interface WebService{
        ActionDoneResponse pushDiagram(Diagram d, String action);
        Diagram pushUserChoice(Diagram d, String action);

        <T> List<T> getAllTByDiagram(int diagramId, E_NarrativeComponent type, Class<T> clazz);
        <T extends NarrativeComponent> int postOrPutT(T component, E_NarrativeComponent type,boolean isPost );

        Drawable getPicture(int sceneId);
    }
}
