package schn.beme.storysummary.mvp.scene;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import lombok.AllArgsConstructor;
import schn.beme.storysummary.mvp.chapter.Chapter;

/**
 * Created by Dorito on 25-07-16.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class Scene {

    @DatabaseField(generatedId = true, columnName = "id")
    int id;
    @DatabaseField(columnName = "chapter_id",
            canBeNull = false,foreign = true,
            foreignAutoRefresh = true,
            maxForeignAutoRefreshLevel = 1)//see third note of foreignautorefresh doc
    Chapter chapterId;
    @DatabaseField(columnName = "position") //TODO rajouter canbeNull=false
    int position;
    @DatabaseField(columnName = "title")
    String title;
    @DatabaseField(columnName = "note",dataType = DataType.LONG_STRING)
    String note;
    @DatabaseField(columnName = "picture")
    String picture;

    public Scene(int id){this.id=id;}

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
