package com.example.trending;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

class WebAdapter  extends RecyclerView.Adapter<WebAdapter.MyViewHolder>{
    Context ct;
    List<MyResource> list;
    public WebAdapter(Button9Activity button9Activity, List<MyResource> resourceList) {
        list = resourceList;
        ct = button9Activity;
    }
    @Override
    public WebAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row,parent,false);
        return new WebAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv.setText(list.get(position).getTechnology());
        holder.yp.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = list.get(position).getVideoid();
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        Button b;
        YouTubePlayerView yp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tecname);
            b = itemView.findViewById(R.id.downloadButton);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  Intent i=new Intent(ct,PdfNewActivity.class);
                    ct.startActivity(i);*/
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(String.valueOf(Uri.parse(list.get(getAdapterPosition()).filepath))));
                    intent.setDataAndType(Uri.parse(list.get(getAdapterPosition()).filepath), mimeType);
                    Intent intent1 = Intent.createChooser(intent, "Open With");
                    ct.startActivity(intent1);
                }
            });
            yp = itemView.findViewById(R.id.youtube_player_view);
        }
    }
}
