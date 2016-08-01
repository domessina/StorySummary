package schn.beme.storysummary.eventbusmsg;

import schn.beme.storysummary.mvp.diagram.DiagramAdapter;

/**
 * Created by Dorito on 18-07-16.
 */
public class ClickDiagramCardEvent {

    public int diagramId;
    public String diagramTitle;
    public boolean isLong=false;
    public DiagramAdapter.DiagramCardVH holder;

    public ClickDiagramCardEvent(int diagramId,String diagramTitle, boolean isLong, DiagramAdapter.DiagramCardVH holder)
    {
     this.diagramId=diagramId;
        this.diagramTitle=diagramTitle;
        this.isLong=isLong;
        this.holder=holder;
    }


}
