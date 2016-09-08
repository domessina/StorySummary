package schn.beme.storysummary.eventbusmsg;

import schn.beme.storysummary.mvp.charactertraits.TraitAdapter;

/**
 * Created by Dorito on 08-09-16.
 */
public class ClickTraitCardEvent {
    public int traitId;
    public TraitAdapter.TraitVH holder;

    public ClickTraitCardEvent(int traitId, TraitAdapter.TraitVH holder){
        this.traitId=traitId;
        this.holder=holder;
    }
}
