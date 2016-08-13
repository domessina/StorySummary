package schn.beme.storysummary.narrativecomponent;

/**
 * Created by Dorito on 13-08-16.
 */
public enum E_NarrativeComponent {
    NC_Diagram("diagram"),
    NC_Chapter("chapter"),
    NC_Scene("scene"),
    NC_Character("character"),
    NC_Trait("trait");

    private String type;

    E_NarrativeComponent(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public String getType(){
        return type;
    }
}
