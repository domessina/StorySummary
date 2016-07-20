package schn.beme.storysummary.mvp.diagram;

/**
 * Created by Dorito on 18-07-16.
 */
public class Diagram {

    public int id;
    public int userId;
    public String title;

//    public Diagram(){}

    public Diagram(int id, String title, int userId)
    {
        this.id=id;
        this.title=title;
        this.userId=userId;
    }
}
