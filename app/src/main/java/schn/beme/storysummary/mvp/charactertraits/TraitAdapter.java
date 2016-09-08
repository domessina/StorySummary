package schn.beme.storysummary.mvp.charactertraits;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.SchnException;
import schn.beme.storysummary.eventbusmsg.ClickTraitCardEvent;
import schn.beme.storysummary.narrativecomponent.Trait;

/**
 * Created by Dorito on 07-09-16.
 */
public class TraitAdapter extends RecyclerView.Adapter<TraitAdapter.TraitVH>{

    private List<Trait> traitList;

    public TraitAdapter(List<Trait> traitList){this.traitList=traitList;}

    @Override
    public TraitVH onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.card_trait, parent, false);
        return new TraitVH(itemView);
    }

    @Override
    public void onBindViewHolder(TraitVH holder, int position) {
        Trait c=traitList.get(position);
        holder.traitId=c.id;
        holder.nameTv.setText(c.name);
    }

    public void addTraitCard(Trait d)
    {
        traitList.add(d);
        notifyItemInserted(traitList.size()-1);
    }

    public void updateContent(Trait c) throws SchnException {
        int pos=traitList.indexOf(new Trait(c.id));
        if(pos==-1)
            throw new SchnException("trait to refresh not found");
        else{
            traitList.set(pos,c);
            notifyItemChanged(pos);
        }
    }

    @Override
    public int getItemCount() {

        return traitList.size();
    }

     /*
    * *****************VIEW HOLDER ***************
    * */

    public class TraitVH extends RecyclerView.ViewHolder {

        protected TextView nameTv;
        public int traitId;

        public TraitVH(View v)
        {
            super(v);
            initViews(v);
            defineActionOnClick(v);
            itemView.setLongClickable(true);
        }

        private void initViews(View v)
        {
            nameTv= (TextView) v.findViewById(R.id.card_trait_name);
        }

        private void defineActionOnClick(final View v) {

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EventBus.getDefault().post(new ClickTraitCardEvent(traitId,TraitVH.this));

                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    EventBus.getDefault().post(new ClickTraitCardEvent(traitId, TraitVH.this));
                    //must  to return false, otherwise context menu doesn't appear
                    return false;

                }
            });
        }


        public Trait removeCard() {
            int pos=getAdapterPosition();
            Trait c=traitList.get(pos);
            traitList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,traitList.size());
            return c;
        }



        public String getTraitName(){
            return nameTv.getText().toString();
        }

    }
}
