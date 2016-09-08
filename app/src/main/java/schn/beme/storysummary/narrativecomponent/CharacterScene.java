package schn.beme.storysummary.narrativecomponent;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Dorito on 07-09-16.
 */
public class CharacterScene {

    @DatabaseField(columnName = "character_id")
    public int characterId;
    @DatabaseField(columnName = "scene_id")
    public int sceneId;

    public CharacterScene(){}

    public CharacterScene(int charId, int sceneId){
        this.characterId=charId;
        this.sceneId=sceneId;
    }
}
