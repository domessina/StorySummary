package schn.beme.storysummary.mvp.diagram;

import java.util.ArrayList;
import java.util.List;

import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;

/**
 * Created by Dorito on 11-07-16.
 */
public class DiagramPresenter extends DefaultActionBarPresenter {


    public DiagramPresenter(View view) {

        super(view);
    }


    public List<Diagram> createList(int size) {

        List<Diagram> result = new ArrayList<>();
        for (int i=1; i <= size; i++) {
            Diagram ci = new Diagram();
            ci.title = "caca"+ i;
            ci.userId =  i;

            result.add(ci);

        }

        return result;
    }

    public interface View extends DefaultActionBarPresenter.View
    {

    }


}
