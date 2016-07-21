package schn.beme.storysummary.mvp.chapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.RemovableCardVH;
import schn.beme.storysummary.eventbusmsg.ClickChapterCardEvent;

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
        holder.titleTv.setText(c.title);
        holder.noteTv.setText(c.note);
        holder.chapter=c;
    }

    public void addChapterCard(Chapter d)
    {
        chapterList.add(d);
        notifyItemInserted(chapterList.size()-1);
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
        protected Chapter chapter;

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
        }

        private void defineActionOnClick(final View v) {

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EventBus.getDefault().post(new ClickChapterCardEvent(chapter.id,false,null));

                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    EventBus.getDefault().post(new ClickChapterCardEvent(chapter.id,true,ChapterVH.this));
                    //must  to return false, otherwise context menu doesn't appear
                    return false;

                }
            });
        }

        @Override
        public void removeCard() {
            int pos=getAdapterPosition();
            chapterList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,chapterList.size());
        }
    }

}
