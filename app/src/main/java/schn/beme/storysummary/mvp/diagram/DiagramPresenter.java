package schn.beme.storysummary.mvp.diagram;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import schn.beme.storysummary.eventbusmsg.ClickCardDiagramEvent;
import schn.beme.storysummary.mvp.defaults.DefaultActionBarPresenter;
import schn.beme.storysummary.StartAndStopAware;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmEditListener;
import schn.beme.storysummary.presenterhelper.dialog.DialogHelper;
import schn.beme.storysummary.presenterhelper.dialog.ConfirmListener;

/**
 * Created by Dorito on 11-07-16.
 */
public class DiagramPresenter extends DefaultActionBarPresenter implements StartAndStopAware, ConfirmListener, ConfirmEditListener {

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

            result.add(new Diagram(i,"caca"+i,0));
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

    //-----------ACTION DELETE CONTEXTUAL MENU-----------


    public void contextMenuDelete()
    {

        DialogHelper.showConfirm("Are you sure?",null,this);
    }

    @Override
    public void accepted() {
        selectedHolder.removeDiagramItem();
    }


    //-----------ACTION CREATE DIAGRAM FAB---------------------

    public void newDiagram()
    {
        DialogHelper.showConfirmEditText("New Diagram", "Title", this);
    }

    @Override
    public void accepted(String input) {

        diagramAdapter.addDiagramCard(new Diagram(diagramAdapter.getItemCount(),input,0));
        ((View)getView()).scrollToEnd();
    }

    //-----------------------------------------------

    public int getLastPositionTotal()
    {
        return diagramAdapter.getItemCount()-1;
    }

    @Override
    public void canceled(){}

    public interface View extends DefaultActionBarPresenter.View
    {
        void scrollToEnd();
    }


}
