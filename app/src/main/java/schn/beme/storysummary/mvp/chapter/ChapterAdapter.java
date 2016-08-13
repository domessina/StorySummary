package schn.beme.storysummary.mvp.chapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.SchnException;
import schn.beme.storysummary.eventbusmsg.ClickChapterCardEvent;
import schn.beme.storysummary.narrativecomponent.Chapter;

/**
 * Created by Dorito on 20-07-16.
 */
public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterVH>{

    private List<Chapter>chapterList;

    public ChapterAdapter(List<Chapter> chapterList)
    {
       this.chapterList=chapterList;
    }

    @Override
    public ChapterVH onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.card_chapter, parent, false);
        return new ChapterVH(itemView);
    }

    @Override
    public void onBindViewHolder(ChapterVH holder, int position) {
        Chapter c=chapterList.get(position);
        holder.iButtonU.setTag(c.id);
        holder.iButtonD.setTag(c.id);
        holder.chapterId=c.id;
        holder.titleTv.setText(c.title);
        holder.noteTv.setText(c.note);

    }

    public void addChapterCard(Chapter d)
    {
        chapterList.add(d);
        notifyItemInserted(chapterList.size()-1);
    }

    public void updateContent(Chapter c) throws SchnException {
        int pos=chapterList.indexOf(new Chapter(c.id));
        if(pos==-1)
            throw new SchnException("diagram to refresh not found");
        else{
            chapterList.set(pos,c);
            notifyItemChanged(pos);
        }
    }

    public int[] getNewFuturePositions(boolean toDown, int chapterId){
        int[] indexes=new int[2];
        indexes[0]=chapterList.indexOf(new Chapter(chapterId));
        indexes[1]=toDown?(indexes[0]+1):(indexes[0]-1);
        //moved chapter from index1, to index2
        return indexes;
    }

    public void swapChapter(int index1, int index2) throws IndexOutOfBoundsException{

        Collections.swap(chapterList,index1,index2);
        notifyItemMoved(index1,index2);
    }


    @Override
    public int getItemCount() {

        return chapterList.size();
    }

    /*
    * *****************VIEW HOLDER ***************
    * */

    public class ChapterVH extends RecyclerView.ViewHolder {

        protected TextView titleTv;
        protected TextView noteTv;
        public int chapterId;
        protected ImageButton iButtonD;
        protected ImageButton iButtonU;

        public ChapterVH(View v)
        {
            super(v);
            initViews(v);
            defineActionOnClick(v);
            itemView.setLongClickable(true);
        }

            private void initViews(View v)
        {
            titleTv= (TextView) v.findViewById(R.id.card_chapter_title);
            noteTv=  (TextView) v.findViewById(R.id.card_chapter_note);
            iButtonD=(ImageButton)v.findViewById(R.id.card_chapter_down);
            iButtonU=(ImageButton)v.findViewById(R.id.card_chapter_up);
        }

        private void defineActionOnClick(final View v) {

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EventBus.getDefault().post(new ClickChapterCardEvent(chapterId,false,ChapterVH.this));

                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    EventBus.getDefault().post(new ClickChapterCardEvent(chapterId,true,ChapterVH.this));
                    //must  to return false, otherwise context menu doesn't appear
                    return false;

                }
            });
        }


        public Chapter removeCard() {
            int pos=getAdapterPosition();
            Chapter c=chapterList.get(pos);
            chapterList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,chapterList.size());
            return c;
        }



        public String getChapterTitle(){
            return titleTv.getText().toString();
        }

        public String getChapterNote(){return noteTv.getText().toString();}
    }

}
