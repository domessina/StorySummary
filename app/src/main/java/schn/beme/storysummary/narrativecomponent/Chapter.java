package schn.beme.storysummary.narrativecomponent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Dorito on 20-07-16.
 */
//supress parce que cette fonctionnalité lombock n'est pas présente dans le java  android
//@AllArgsConstructor(suppressConstructorProperties = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chapter extends NarrativeComponent implements Comparable<Chapter> {


    @DatabaseField(generatedId = true, columnName = "id")
    public int id;

    @JsonProperty("id")
    @DatabaseField(columnName = "server_id",defaultValue = "-1")
    public Integer serverId;

    @JsonProperty("phase")
    @DatabaseField(columnName = "phase")
    public short phase;     //TODO field private avec oremlite ça irait?

    @JsonProperty("title")
    @DatabaseField(columnName = "title")
    public String title;

    @JsonProperty("position")
    @DatabaseField(columnName = "position",canBeNull = false)
    public int position;

    @JsonProperty("note")
    @DatabaseField(columnName = "note", dataType = DataType.LONG_STRING)
    public String note;

    //ORMLite only holds id field of diagram, so diagramId will have almost the size of an int
    @JsonIgnore
    @DatabaseField(columnName = "diagram_id",
            canBeNull = false, foreign = true,
            foreignAutoRefresh = false,
            columnDefinition = "integer references diagram(id) on delete cascade")
    public Diagram diagramId;

    @JsonProperty("diagramId")
    public int diagramIdForSync;

    public Chapter(int id){this.id=id;}

    public Chapter(int id, short phase, String title,int position, String note, Diagram diagramId ){
        this.id=id;
        this.phase=phase;
        this.title=title;
        this.position=position;
        this.note=note;
        this.diagramId=diagramId;
    }

    //ormLite need it
    public Chapter(){}



    @Override
    public boolean equals(Object obj) {

        if(obj==this)
            return true;
        else if(this.id==((Chapter)obj).id)
            return true;
        else
            return false;
    }

    @Override
    public int compareTo(Chapter chapter) {

        return this.position-chapter.position;
    }
}
