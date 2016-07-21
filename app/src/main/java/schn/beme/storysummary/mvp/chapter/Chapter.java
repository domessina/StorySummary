package schn.beme.storysummary.mvp.chapter;

import lombok.AllArgsConstructor;

/**
 * Created by Dorito on 20-07-16.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class Chapter {
    public int id;
    public short phase;
    public String title;
    public int position;
    public int diagramId;
    public String note;


}
