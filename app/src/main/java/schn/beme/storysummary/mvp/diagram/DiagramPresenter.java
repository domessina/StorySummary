package schn.beme.storysummary.mvp.diagram;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.eventbusmsg.ClickCardDiagramEvent;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.StartAndStopAware;

/**
 * Created by Dorito on 11-07-16.
 */
public class DiagramPresenter extends DefaultActionBarPresenter implements StartAndStopAware {


    public DiagramPresenter(View view) {

        super(view);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);       //TODO quand utiliser unregister(this) pour les presenters? car dans les fragments et ac c'est dans onstart et onstop
    }

    @Subscribe
    public void onClickCardDiagramEvent(ClickCardDiagramEvent event)
    {

        Toast.makeText(MyApplication.getCurntActivityContext(), String.valueOf(event.diagramId), Toast.LENGTH_SHORT).show();
    }


    public List<Diagram> createList(int size) {

        List<Diagram> result = new ArrayList<>();
        for (int i=1; i <= size; i++) {
            Diagram ci = new Diagram();
            ci.title = "caca"+ i;
            ci.id=i;

            result.add(ci);

        }

        return result;
    }



    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    public interface View extends DefaultActionBarPresenter.View
    {

    }


}
