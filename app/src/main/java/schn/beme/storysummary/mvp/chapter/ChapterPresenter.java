package schn.beme.storysummary.mvp.chapter;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import schn.beme.storysummary.RemovableCardVH;
import schn.beme.storysummary.StartAndStopAware;
import schn.beme.storysummary.eventbusmsg.ClickChapterCardEvent;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.presenterhelper.IntentHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditListener;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogHelper;

/**
 * Created by Dorito on 20-07-16.
 */
public class ChapterPresenter extends DefaultActionBarPresenter implements ConfirmListener, ConfirmEditListener, StartAndStopAware {

    private ChapterAdapter chapterAdapter;
    private RemovableCardVH selectedHolder;
    public int lastDiagramIdTouched;

    public ChapterPresenter(View view) {

        super(view);
        chapterAdapter=new ChapterAdapter(createList(21));

    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);       //TODO quand utiliser unregister(this) pour les presenters? car dans les fragments et ac c'est dans onstart et onstop
    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }


    public List<Chapter> createList(int size) {

        List<Chapter> result = new ArrayList<>();
        for (int i=1; i <= size; i++) {

            result.add(new Chapter(i,(short)0,"ttie"+i,i,i,"notes"));
        }

        return result;
    }

    public ChapterAdapter getChapterAdapter(){return this.chapterAdapter;}

    @Subscribe
    public void onClickChapterCardEvent(ClickChapterCardEvent event)
    {

        lastDiagramIdTouched=event.chapterId;
        selectedHolder=event.holder;
        if(!event.isLong) {
            IntentHelper.getInstance().startActivityNoFlags(ChapterActivity.class,null);
        }
    }

    public int getLastPositionTotal()
    {
        return chapterAdapter.getItemCount()-1;
    }

    @Override
    public void canceled() {}

    //-----------ACTION DELETE CONTEXTUAL MENU-----------


    public void contextMenuDelete()
    {
        DialogHelper.showConfirm("Are you sure?",null,this);
    }

    @Override
    public void accepted() {
        selectedHolder.removeCard();
    }

    //-----------ACTION CREATE DIAGRAM FAB---------------------

    public void newChapter()
    {
        DialogHelper.showConfirmEditText("New Chapter", "Title",false, this);
    }

    @Override
    public void accepted(String input) {

        chapterAdapter.addChapterCard(new Chapter(chapterAdapter.getItemCount(),(short)0,"jj",0,0,input));
        ((View)getView()).scrollToEnd();
    }

    public interface View extends DefaultActionBarPresenter.View
    {
        void scrollToEnd();
    }
}


