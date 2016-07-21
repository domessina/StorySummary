package schn.beme.storysummary.eventbusmsg;

import schn.beme.storysummary.RemovableCardVH;

/**
 * Created by Dorito on 21-07-16.
 */
public class ClickChapterCardEvent {

    public int chapterId;
    public boolean isLong=false;
    public RemovableCardVH holder;

    public ClickChapterCardEvent(int chapterId, boolean isLong, RemovableCardVH holder) {

        this.chapterId=chapterId;
        this.isLong=isLong;
        this.holder=holder;
    }
}
