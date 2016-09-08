package schn.beme.storysummary.eventbusmsg;

import schn.beme.storysummary.mvp.character.CharacterAdapter;

/**
 * Created by Dorito on 07-09-16.
 */
public class ClickCharacterCardEvent {

    public int characterId;
    public CharacterAdapter.CharacterVH holder;
    public boolean isLong;

    public ClickCharacterCardEvent(int id, CharacterAdapter.CharacterVH holder, boolean isLong){
        this.characterId=id;
        this.holder=holder;
        this.isLong=isLong;
    }

}
