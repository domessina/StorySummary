package schn.beme.storysummary.narrativecomponent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Dorito on 02-09-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character extends NarrativeComponent {

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;

    @JsonProperty("id")
    @DatabaseField(columnName = "server_id", defaultValue = "-1")
    public Integer serverId;
    @DatabaseField(columnName = "scene_id",
            canBeNull = false,foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references scene(id) on delete cascade")
    public Scene sceneId;

    @JsonIgnore
    @DatabaseField(columnName = "diagram_id",
            canBeNull = false, foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references diagram(id) on delete cascade")
    public Diagram diagramId;

    @JsonProperty("diagramId")
    public int diagramIdForSync;

    @JsonProperty("name")
    @DatabaseField(columnName = "name")
    public String name;

    @JsonProperty("type")
    @DatabaseField(columnName = "type")
    public String type;

    @JsonProperty("note")
    @DatabaseField(columnName = "note",dataType = DataType.LONG_STRING)
    public String note;

    @JsonProperty("picture")
    @DatabaseField(columnName = "picture")
    public String picture;

    public Character(){}

    public Character(int id){
        this.id=id;
    }


}
