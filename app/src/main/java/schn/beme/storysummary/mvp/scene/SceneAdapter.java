package schn.beme.storysummary.mvp.scene;

import android.content.ContentResolver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.SchnException;
import schn.beme.storysummary.eventbusmsg.ClickSceneCardEvent;
import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Scene;

/**
 * Created by Dorito on 29-07-16.
 */
public class SceneAdapter extends RecyclerView.Adapter<SceneAdapter.SceneVH>{

    private List<Scene> sceneList;

    public SceneAdapter(List<Scene> sceneList){this.sceneList=sceneList;}

    @Override
    public SceneVH onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.card_scene, parent, false);
        return new SceneVH(itemView);
    }

    @Override
    public void onBindViewHolder(SceneVH holder, int position) {
        Scene s=sceneList.get(position);
        holder.sceneId=s.id;
        holder.titleTv.setText(s.title);
        holder.noteTv.setText(s.note);
        holder.imgV.setImageResource(R.drawable.salope);
//        holder.imgV.setImageURI(Uri.parse(new File("/external/media/1366.jpg").toString()));

    }

    public void updateContent(Scene c) throws SchnException {
        int pos=sceneList.indexOf(new Scene(c.id));
        if(pos==-1)
            throw new SchnException("scene to refresh not found");
        else{
            sceneList.set(pos,c);
            notifyItemChanged(pos);
        }
    }

    @Override
    public int getItemCount() {

        return sceneList.size();
    }

    public void addSceneCard(Scene s){
        sceneList.add(s);
        notifyItemInserted(sceneList.size()-1);
    }

    /*
      * *****************VIEW HOLDER ***************
      * */
    public class SceneVH extends RecyclerView.ViewHolder{

        protected TextView titleTv;
        protected TextView noteTv;
        public int sceneId;
        protected ImageView imgV;


        public SceneVH(View v){
            super(v);
            initViews(v);
            defineActionOnClick(v);
            itemView.setLongClickable(true);
        }

        private void initViews(View v){
            titleTv=(TextView)v.findViewById(R.id.card_scene_title);
            noteTv=(TextView)v.findViewById(R.id.card_scene_note);
            imgV=(ImageView)v.findViewById(R.id.card_scene_picture);

//            Picasso.with(imgV.getContext()).load()
        }

        private void defineActionOnClick(final View v) {

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EventBus.getDefault().post(new ClickSceneCardEvent(sceneId,false,SceneVH.this));

                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    EventBus.getDefault().post(new ClickSceneCardEvent(sceneId,true,SceneVH.this));
                    //must  to return false, otherwise context menu doesn't appear
                    return false;

                }
            });
        }


        public Scene removeCard() {
            int pos=getAdapterPosition();
            Scene c=sceneList.get(pos);
            sceneList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,sceneList.size());
            return c;
        }



        public String getSceneTitle(){
            return titleTv.getText().toString();
        }

        public String getSceneNote(){return noteTv.getText().toString();}
    }
}
