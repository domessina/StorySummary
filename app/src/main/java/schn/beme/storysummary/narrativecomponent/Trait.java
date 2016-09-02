package schn.beme.storysummary.narrativecomponent;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Dorito on 02-09-16.
 */
public class Trait extends NarrativeComponent {


    @DatabaseField(generatedId = true, columnName = "id")
    public int id;
    @DatabaseField(columnName = "character_id",
            canBeNull = false, foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references character(id) on delete cascade")
    public Character characterId;
    @DatabaseField(columnName = "diagram_id",
            canBeNull = false, foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references diagram(id) on delete cascade")
    public Diagram diagramId;
    @DatabaseField(columnName = "name")
    public String name;

    public Trait(){}
}
