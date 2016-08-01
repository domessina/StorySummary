package schn.beme.storysummary.eventbusmsg;

import schn.beme.storysummary.mvp.chapter.ChapterAdapter;

/**
 * Created by Dorito on 21-07-16.
 */
public class ClickChapterCardEvent {

    public int chapterId;
    public boolean isLong=false;
    public ChapterAdapter.ChapterVH holder;

    public ClickChapterCardEvent(int chapterId, boolean isLong, ChapterAdapter.ChapterVH holder) {

        this.chapterId=chapterId;
        this.isLong=isLong;
        this.holder=holder;
    }
}
