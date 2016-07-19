package schn.beme.storysummary.eventbusmsg;

import schn.beme.storysummary.mvp.diagram.DiagramAdapter;

/**
 * Created by Dorito on 18-07-16.
 */
public class ClickCardDiagramEvent {

    public int diagramId;
    public boolean isLong=false;
    public DiagramAdapter.DiagramViewHolder holder;

    public ClickCardDiagramEvent(int diagramId, boolean isLong, DiagramAdapter.DiagramViewHolder holder)
    {
     this.diagramId=diagramId;
        this.isLong=isLong;
        this.holder=holder;
    }


}
