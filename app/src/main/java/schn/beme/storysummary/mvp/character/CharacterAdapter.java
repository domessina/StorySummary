package schn.beme.storysummary.mvp.character;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.SchnException;
import schn.beme.storysummary.eventbusmsg.ClickChapterCardEvent;
import schn.beme.storysummary.eventbusmsg.ClickCharacterCardEvent;
import schn.beme.storysummary.narrativecomponent.Character;
import schn.beme.storysummary.narrativecomponent.Diagram;


/**
 * Created by Dorito on 07-09-16.
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterVH>
{
    private List<Character> characterList;

    public CharacterAdapter(List<Character> characterList){this.characterList=characterList;}

    @Override
    public CharacterVH onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.card_character, parent, false);
        return new CharacterVH(itemView);
    }

    @Override
    public void onBindViewHolder(CharacterVH holder, int position) {
        Character c=characterList.get(position);
        holder.characterId=c.id;
        holder.nameTv.setText(c.name);
        holder.noteTv.setText(c.note);
    }

    public void addCharacterCard(Character d)
    {
        characterList.add(d);
        notifyItemInserted(characterList.size()-1);
    }

    public void updateContent(Character c) throws SchnException {
        int pos=characterList.indexOf(new Character(c.id));
        if(pos==-1)
            throw new SchnException("character to refresh not found");
        else{
            characterList.set(pos,c);
            notifyItemChanged(pos);
        }
    }

    @Override
    public int getItemCount() {

        return characterList.size();
    }

     /*
    * *****************VIEW HOLDER ***************
    * */

    public class CharacterVH extends RecyclerView.ViewHolder {

        protected TextView nameTv;
        protected TextView noteTv;
        public int characterId;

        public CharacterVH(View v)
        {
            super(v);
            initViews(v);
            defineActionOnClick(v);
            itemView.setLongClickable(true);
        }

        private void initViews(View v)
        {
            nameTv= (TextView) v.findViewById(R.id.card_char_name);
            noteTv=  (TextView) v.findViewById(R.id.card_char_note);
        }

        private void defineActionOnClick(final View v) {

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EventBus.getDefault().post(new ClickCharacterCardEvent(characterId,CharacterVH.this,false));

                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    EventBus.getDefault().post(new ClickCharacterCardEvent(characterId, CharacterVH.this,true));
                    //must  to return false, otherwise context menu doesn't appear
                    return false;

                }
            });
        }


        public Character removeCard() {
            int pos=getAdapterPosition();
            Character c=characterList.get(pos);
            characterList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,characterList.size());
            return c;
        }



        public String getCharacterName(){
            return nameTv.getText().toString();
        }
        public String getCharacterNote(){return noteTv.getText().toString();}

    }
}
