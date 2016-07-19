package schn.beme.storysummary.mvp.diagram;

/**
 * Created by Dorito on 18-07-16.
 */
public class Diagram {

    public int id;
    public int userId;
    public String title;

    @Override
    public boolean equals(Object o)
    {
        if (o == null) return false;
        if (!(o instanceof Diagram))return false;
        if (o == this) return true;
        if (this.id==((Diagram)o).id) return true;
        return false;
    }
}
