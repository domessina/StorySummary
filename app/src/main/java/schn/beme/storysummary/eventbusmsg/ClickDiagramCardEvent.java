package schn.beme.storysummary.eventbusmsg;

import android.support.v7.widget.RecyclerView;

import schn.beme.storysummary.RemovableCardVH;
import schn.beme.storysummary.mvp.diagram.DiagramAdapter;

/**
 * Created by Dorito on 18-07-16.
 */
public class ClickDiagramCardEvent {

    public int diagramId;
    public String diagramTitle;
    public boolean isLong=false;
    public RemovableCardVH holder;

    public ClickDiagramCardEvent(int diagramId,String diagramTitle, boolean isLong, RemovableCardVH holder)
    {
     this.diagramId=diagramId;
        this.diagramTitle=diagramTitle;
        this.isLong=isLong;
        this.holder=holder;
    }


}
