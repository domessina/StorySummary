package schn.beme.storysummary.narrativecomponent;

import com.j256.ormlite.field.DatabaseField;

import lombok.AllArgsConstructor;

/**
 * Created by Dorito on 18-07-16.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class Diagram extends NarrativeComponent{

    @DatabaseField(generatedId = true, columnName = "id") //TODO indexer seulement les id des scènes car ce ser ala colonne la plus utilisée ET plus pleine?
    public int id;
//    public int serverId;
    @DatabaseField(columnName = "title")
    public String title;
    @DatabaseField(columnName = "user_id")
    public int userId;


    public Diagram(int id)
    {
        this.id=id;
    }

    //ormLite need it
    public Diagram(){}

    /*public Diagram(int id, String title, int userId){
        this.id=id;
        this.title=title;
        this.userId=userId;
    }*/

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
