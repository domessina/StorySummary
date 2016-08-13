package schn.beme.storysummary.narrativecomponent;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import lombok.AllArgsConstructor;

/**
 * Created by Dorito on 25-07-16.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class Scene extends NarrativeComponent {

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;
    @DatabaseField(columnName = "chapter_id",
            canBeNull = false,foreign = true,
            foreignAutoRefresh = false)//see third note of foreignautorefresh doc maxForeignAutoRefreshLevel = 1
    public Chapter chapterId;
    @DatabaseField(columnName = "position") //TODO rajouter canbeNull=false
    public int position;
    @DatabaseField(columnName = "title")
    public String title;
    @DatabaseField(columnName = "note",dataType = DataType.LONG_STRING)
    public String note;
    @DatabaseField(columnName = "picture")
    public String picture;

    public Scene(int id){this.id=id;}

   /* public Scene(int id, Chapter chapterId, int position, String title, String note, String picture){
        this.id=id;
        this.chapterId=chapterId;
        this.position=position;
        this.title=title;
        this.note=note;
        this.picture=picture;
    }*/

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
