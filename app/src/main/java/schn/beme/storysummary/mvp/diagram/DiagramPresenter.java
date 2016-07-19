package schn.beme.storysummary.mvp.diagram;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.eventbusmsg.ClickCardDiagramEvent;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.StartAndStopAware;
import schn.beme.storysummary.presenterhelper.ConfirmDialog;
import schn.beme.storysummary.presenterhelper.ConfirmListener;

/**
 * Created by Dorito on 11-07-16.
 */
public class DiagramPresenter extends DefaultActionBarPresenter implements StartAndStopAware, ConfirmListener {

    private DiagramAdapter diagramAdapter;
    private DiagramAdapter.DiagramViewHolder selectedHolder;
    public int lastDiagramIdTouched;

    public DiagramPresenter(View view) {

        super(view);
        diagramAdapter=new DiagramAdapter(createList(20));
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);       //TODO quand utiliser unregister(this) pour les presenters? car dans les fragments et ac c'est dans onstart et onstop
    }

    @Subscribe
    public void onClickCardDiagramEvent(ClickCardDiagramEvent event)
    {

        lastDiagramIdTouched=event.diagramId;
        selectedHolder=event.holder;
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

    public DiagramAdapter getDiagramAdapter()
    {
        return  this.diagramAdapter;
    }

    //-----------ACTIONS EDIT AND DELETE CONTEXTUAL MENU-----------


    public void contextMenuDelete()
    {

        ConfirmDialog.show(this);
    }

    @Override
    public void accepted() {
        Toast.makeText(MyApplication.getCurntActivityContext(), "oui", Toast.LENGTH_SHORT).show();
        selectedHolder.removeDiagramItem();
    }

    @Override
    public void canceled() {
        Toast.makeText(MyApplication.getCurntActivityContext(), "non", Toast.LENGTH_SHORT).show();
    }


    //--------------------------------------------------------

    public interface View extends DefaultActionBarPresenter.View
    {

    }


}
