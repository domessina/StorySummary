package schn.beme.storysummary.mvp.diagram;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.RemovableCardVH;
import schn.beme.storysummary.eventbusmsg.ClickDiagramCardEvent;

/**
 * Created by Dorito on 17-07-16.
 */
public class DiagramAdapter extends RecyclerView.Adapter<DiagramAdapter.DiagramCardVH> {

    private List<Diagram> diagramList;

    public DiagramAdapter(List<Diagram> diagramList) {

        this.diagramList = diagramList;
    }

    @Override
    public int getItemCount() {

        return diagramList.size();
    }



    @Override
    public DiagramCardVH onCreateViewHolder(ViewGroup viewGroup, int i) {

        /*LayoutInflater inflater = (LayoutInflater) MyApplication
                .getCrntActivityContext()
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );*/

        LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.card_diagram, viewGroup, false);
        return new DiagramCardVH(itemView);
    }


    @Override
    public void onBindViewHolder(DiagramCardVH diagramVH, int i) {

        Diagram ci = diagramList.get(i);
        diagramVH.titleTv.setText(ci.title);
        diagramVH.IdTv.setText(String.valueOf(ci.id));
        diagramVH.diagram=ci;
    }

    public void addDiagramCard(Diagram d)
    {
        diagramList.add(d);
        notifyItemInserted(diagramList.size()-1);
    }




    //---------------VIEW HOLDER--------------
    public class DiagramCardVH extends RecyclerView.ViewHolder implements RemovableCardVH {
        protected TextView titleTv;
        protected TextView IdTv;
        protected Diagram diagram;

        public DiagramCardVH(View v) {
            super(v);
            initViews(v);
            defineActionOnClick(v);
            //for showing contextual menu
            //itemView is the view passed to super(v)
            itemView.setLongClickable(true);

        }

        private void initViews(View v)
        {
            titleTv =  (TextView) v.findViewById(R.id.card_diagram_title);
            IdTv = (TextView) v.findViewById(R.id.card_diagram_id);
        }

        private void defineActionOnClick(final View v) {

          v.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  EventBus.getDefault().post(new ClickDiagramCardEvent(diagram.id,diagram.title,false,null));

              }
          });

          v.setOnLongClickListener(new View.OnLongClickListener() {
              @Override
              public boolean onLongClick(View view) {
                  EventBus.getDefault().post(new ClickDiagramCardEvent(diagram.id,null,true,DiagramCardVH.this));
                    //must  to return false, otherwise context menu doesn't appear
                  return false;

              }
          });
        }

        @Override
        public Diagram removeCard() {
            int pos=getAdapterPosition();
            Diagram d=diagramList.get(pos);
            diagramList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,diagramList.size());
            return d;
        }
    }
}



