package schn.beme.storysummary.narrativecomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Dorito on 25-07-16.
 */
@JsonIgnoreProperties(value ={"id"}, ignoreUnknown = true)
public class Scene extends NarrativeComponent {

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;

    @JsonProperty("id")
    @DatabaseField(columnName = "server_id", defaultValue = "-1")
    public int serverId;

    @JsonProperty("chapterId")
    @DatabaseField(columnName = "chapter_id",
            canBeNull = false,foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references chapter(id) on delete cascade")//see third note of foreignautorefresh doc maxForeignAutoRefreshLevel = 1
    public Chapter chapterId;

    @JsonProperty("diagramId")
    @DatabaseField(columnName = "diagram_id",
            canBeNull = false, foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references diagram(id) on delete cascade")
    public Diagram diagramId;

    @JsonProperty("position")
    @DatabaseField(columnName = "position") //TODO rajouter canbeNull=false
    public int position;

    @JsonProperty("tag")//on server side it's tag and not title
    @DatabaseField(columnName = "title")
    public String title;

    @JsonProperty("note")
    @DatabaseField(columnName = "note",dataType = DataType.LONG_STRING)
    public String note;

    @JsonProperty("picture")
    @DatabaseField(columnName = "picture")
    public String picture;

    public Scene(int id){this.id=id;}

    public Scene(int id, Chapter chapterId, Diagram diagramId, int position, String title, String note, String picture){
        this.id=id;
        this.chapterId=chapterId;
        this.diagramId=diagramId;
        this.position=position;
        this.title=title;
        this.note=note;
        this.picture=picture;
    }

    public Scene(){}



    @Override
    public boolean equals(Object obj) {

        if(obj==this)
            return true;
        else if(this.id==((Scene)obj).id)
            return true;
        else
            return false;
    }
}
