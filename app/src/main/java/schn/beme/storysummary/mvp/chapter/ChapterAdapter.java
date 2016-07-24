package schn.beme.storysummary.mvp.chapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.RemovableCardVH;
import schn.beme.storysummary.eventbusmsg.ClickChapterCardEvent;
import schn.beme.storysummary.mvp.diagram.Diagram;

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
//        holder.noteTv.setText(c.note);
        holder.noteTv.setText(String.valueOf(position));

    }

    public void addChapterCard(Chapter d)
    {
        chapterList.add(d);
        notifyItemInserted(chapterList.size()-1);
    }

    public int[] moveChapter(boolean toDown, int chapterId) throws IndexOutOfBoundsException{
        int index1=chapterList.indexOf(new Chapter(chapterId));
        int index2;
        if(toDown) index2=index1+1;
        else        index2=index1-1;
        Collections.swap(chapterList,index1,index2);
        notifyItemMoved(index1,index2);
        //moved chapter from index1, to index2
        return new int[]{index1,index2};
    }


    @Override
    public int getItemCount() {

        return chapterList.size();
    }

    /*
    * *****************VIEW HOLDER ***************
    * */

    public class ChapterVH extends RecyclerView.ViewHolder implements RemovableCardVH{

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

                    EventBus.getDefault().post(new ClickChapterCardEvent(chapterId,false,null));

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

        @Override
        public Chapter removeCard() {
            int pos=getAdapterPosition();
            Chapter c=chapterList.get(pos);
            chapterList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,chapterList.size());
            return c;
        }
    }

}
