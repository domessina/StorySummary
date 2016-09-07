package schn.beme.storysummary.narrativecomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Dorito on 02-09-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trait extends NarrativeComponent {


    @DatabaseField(generatedId = true, columnName = "id")
    public int id;

    @JsonProperty("id")
    @DatabaseField(columnName = "server_id",defaultValue = "-1")
    public Integer serverId;

    @JsonProperty("characterId")
    @DatabaseField(columnName = "character_id",
            canBeNull = false, foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references character(id) on delete cascade")
    public Character characterId;

    @JsonProperty("diagramId")
    @DatabaseField(columnName = "diagram_id",
            canBeNull = false, foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references diagram(id) on delete cascade")
    public Diagram diagramId;

    @JsonProperty("name")
    @DatabaseField(columnName = "name")
    public String name;

    public Trait(){}
}
