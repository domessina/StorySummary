package schn.beme.storysummary.mvp.diagram;

import com.j256.ormlite.field.DatabaseField;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;

/**
 * Created by Dorito on 18-07-16.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class Diagram {

    @DatabaseField(generatedId = true, columnName = "id") //TODO indexer seulement les id des scènes car ce ser ala colonne la plus utilisée ET plus pleine?
    public int id;
//    public int serverId;
    @DatabaseField(columnName = "title")
    public String title;
    @DatabaseField(columnName = "user_id")
    public int userId;

    //ormLite need it
    public Diagram(){}
}
