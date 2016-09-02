package schn.beme.storysummary.narrativecomponent;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Dorito on 02-09-16.
 */
public class Character extends NarrativeComponent {

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;
    @DatabaseField(columnName = "scene_id",
            canBeNull = false,foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references scene(id) on delete cascade")
    public Scene sceneId;
    @DatabaseField(columnName = "diagram_id",
            canBeNull = false, foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references diagram(id) on delete cascade")
    public Diagram diagramId;
    @DatabaseField(columnName = "name")
    public String name;
    @DatabaseField(columnName = "type")
    public String type;
    @DatabaseField(columnName = "note",dataType = DataType.LONG_STRING)
    public String note;
    @DatabaseField(columnName = "picture_id")
    public String pictureId;

    public Character(){}
}
