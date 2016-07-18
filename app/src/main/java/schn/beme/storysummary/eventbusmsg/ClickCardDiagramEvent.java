package schn.beme.storysummary.eventbusmsg;

/**
 * Created by Dorito on 18-07-16.
 */
public class ClickCardDiagramEvent {

    public int diagramId;

    public ClickCardDiagramEvent(int diagramId)
    {
     this.diagramId=diagramId;
    }
}
