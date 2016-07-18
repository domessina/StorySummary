package schn.beme.storysummary.mvp.diagram;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import schn.beme.be.storysummary.R;

/**
 * Created by Dorito on 17-07-16.
 */
public class DiagramAdapter extends RecyclerView.Adapter<DiagramAdapter.DiagramViewHolder> {

    private List<Diagram> contactList;

    public DiagramAdapter(List<Diagram> contactList) {

        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {

        return contactList.size();
    }



    @Override
    public DiagramViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        /*LayoutInflater inflater = (LayoutInflater) MyApplication
                .getCurntActivityContext()
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );*/

        LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());

        View itemView = inflater.inflate(R.layout.card_diagram, viewGroup, false);


        return new DiagramViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(DiagramViewHolder diagramViewHolder, int i) {

        Diagram ci = contactList.get(i);
        diagramViewHolder.titleTv.setText(ci.title);
        diagramViewHolder.userIdTv.setText(String.valueOf(ci.userId));
    }


    //---------------VIEW HOLDER--------------
    public class DiagramViewHolder extends RecyclerView.ViewHolder {
        protected TextView titleTv;
        protected TextView userIdTv;

        public DiagramViewHolder(View v) {
            super(v);
            titleTv =  (TextView) v.findViewById(R.id.card_diagram_title);

            userIdTv = (TextView) v.findViewById(R.id.card_diagram_user);
        }
    }
}



