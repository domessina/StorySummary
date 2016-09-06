package schn.beme.storysummary.narrativecomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Dorito on 18-07-16.
 */
@JsonIgnoreProperties(value ={"needSynch","action","enabled"}, ignoreUnknown = true)
public class Diagram extends NarrativeComponent{

    @DatabaseField(generatedId = true, columnName = "id") //TODO indexer seulement les id des scènes car ce ser ala colonne la plus utilisée ET plus pleine?
    public int id;

    @JsonProperty("id")
    @DatabaseField(columnName = "server_id", defaultValue = "-1")
    public Integer serverId;

    @JsonProperty("title")
    @DatabaseField(columnName = "title")
    public String title;

    @JsonProperty("userId")
    @DatabaseField(columnName = "user_id")
    public int userId;

    @JsonProperty("pictureId")
    @DatabaseField(columnName = "picture_id")
    public String pictureId;

    @DatabaseField(columnName = "need_synch")
    public boolean needSynch;

    @DatabaseField(columnName = "action")
    public String action;

    @DatabaseField(columnName = "enabled")
    public boolean enabled;

    public Diagram(int id)
    {
        this.id=id;
    }

    //ormLite need it
    public Diagram(){}

    public Diagram(int id, String title, int userId){
        this.id=id;
        this.title=title;
        this.userId=userId;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj==this)
            return true;
        else if(this.id==((Diagram)obj).id)
            return true;
        else
            return false;
    }
}
