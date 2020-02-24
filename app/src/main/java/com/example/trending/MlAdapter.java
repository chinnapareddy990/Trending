package com.example.trending;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

public class MlAdapter extends RecyclerView.Adapter<MlAdapter.MyViewHolder>{
    Context ct;
    List<MyResource> list;
    public MlAdapter(Button1Activity button1Activity, List<MyResource> resourceList) {
        list = resourceList;
        ct = button1Activity;
    }


    @Override
    public MlAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MlAdapter.MyViewHolder holder, final int position) {

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
                   /* Intent i=new Intent(ct,PdfViewActivity.class);
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
