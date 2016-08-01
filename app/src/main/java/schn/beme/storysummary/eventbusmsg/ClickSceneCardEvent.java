package schn.beme.storysummary.eventbusmsg;

import schn.beme.storysummary.mvp.scene.SceneAdapter;

/**
 * Created by Dorito on 29-07-16.
 */
public class ClickSceneCardEvent {

    public int sceneId;
    public boolean isLong=false;
    public SceneAdapter.SceneVH holder;

    public ClickSceneCardEvent(int sceneId, boolean isLong, SceneAdapter.SceneVH holder)
    {
        this.sceneId=sceneId;
        this.isLong=isLong;
        this.holder=holder;
    }



}
