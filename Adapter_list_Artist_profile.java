package com.ArashTorDev.tablo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_list_Artist_profile extends RecyclerView.Adapter<Adapter_list_Artist_profile.myViewHolder> {

    List<Model_list_artists_profile> model_list_artists_profile;

    public Adapter_list_Artist_profile(List<Model_list_artists_profile> model_list_artists_profile) {
        this.model_list_artists_profile = model_list_artists_profile;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View myView = inflater.inflate(R.layout.row_artist_list_main_activity, parent, false);
        myViewHolder holder = new myViewHolder(myView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Model_list_artists_profile currentModel = model_list_artists_profile.get(position);
        holder.im_ArtistProfile.setImageBitmap(currentModel.getArtistProfilePic());
        holder.tv_ArtistName.setText(String.format("%s %s",currentModel.getArtistName(),currentModel.getArtistLastName()));
        holder.tv_ArtistMethod.setText(currentModel.getArtistMethod());
        holder.artistRating.setRating(currentModel.getArtistRating());
    }


    @Override
    public int getItemCount() {
        return model_list_artists_profile.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView im_ArtistProfile;
        TextView tv_ArtistName, tv_ArtistMethod;
        RatingBar artistRating;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            im_ArtistProfile = itemView.findViewById(R.id.im_ArtistProfilePic);
            tv_ArtistName = itemView.findViewById(R.id.tv_ArtistName);
            tv_ArtistMethod = itemView.findViewById(R.id.tv_ArtistMethod);
            artistRating = itemView.findViewById(R.id.artistRating);
        }
    }
}
