package schn.beme.storysummary.mvp.chapter;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import lombok.AllArgsConstructor;
import schn.beme.storysummary.mvp.diagram.Diagram;

/**
 * Created by Dorito on 20-07-16.
 */
//supress parce que cette fonctionnalité n'est pas présente dans le java  android
@AllArgsConstructor(suppressConstructorProperties = true)
public class Chapter {

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;
//    @DatabaseField(columnName = "server_id",defaultValue = "-1")
//    public int serverId;
    @DatabaseField(columnName = "phase")
    public short phase;     //TODO field private avec oremlite ça irait?
    @DatabaseField(columnName = "title")
    public String title;
    @DatabaseField(columnName = "position")
    public int position;

    //ORMLite only holds id field of diagram, so diagramId will have almost the size of an int
    @DatabaseField(columnName = "diagram_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public Diagram diagramId;
    @DatabaseField(columnName = "note", dataType = DataType.LONG_STRING)
    public String note;


    //ormLite need it
    public Chapter(){}
}
