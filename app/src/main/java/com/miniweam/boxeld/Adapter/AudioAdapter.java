package com.miniweam.boxeld.Adapter;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miniweam.boxeld.Models.Users;
import com.miniweam.boxeld.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    private ArrayList<Users> list;
    Context context;

    private ArrayList<MediaStore.Audio> audioList;

    public AudioAdapter(ArrayList<MediaStore.Audio> audioList, Context context) {
        this.audioList = audioList;
        this.context = context;
    }

    @NonNull
    @Override
    public AudioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_show_user_audio, parent, false);
        //View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user_audio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioAdapter.ViewHolder holder, int position) {
        Users audio= list.get(position);
        Picasso.get().load(audio.getProfilePic()).placeholder(R.drawable.ic_audio_file).into(holder.image);
        //MediaStore.Audio audio = audioList.get(position);
        holder.audioFileTitle.setText(audio.getAudioFileTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        TextView audioFileTitle, userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.content_image);
            audioFileTitle = itemView.findViewById(R.id.audio_file_title);
            userName = itemView.findViewById(R.id.user_name);

        }
    }
}
