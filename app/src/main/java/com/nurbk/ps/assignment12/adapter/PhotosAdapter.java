package com.nurbk.ps.assignment12.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nurbk.ps.assignment12.R;
import com.nurbk.ps.assignment12.model.Photo;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {

    private List<Photo> photoList;
    private OnClickListener onClickListener;

    public PhotosAdapter(List<Photo> photoList, OnClickListener onClickListener) {
        this.photoList = photoList;
        this.onClickListener = onClickListener;
    }

    class PhotosViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView image;

        PhotosViewHolder(View item) {
            super(item.getRootView());
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }


        public void bind(Photo photo, OnClickListener onClickListener) {
            title.setText(photo.getTitle());
            Glide.with(itemView.getContext())
                    .load(getUrl(photo))
                    .into(image);

            itemView.setOnClickListener(v -> onClickListener.onClick(photo));

        }

        private String getUrl(Photo photo) {
            return "https://live.staticflickr.com/" + photo.getServer()
                    + "/" + photo.getId() + "_" + photo.getSecret() + "_w.jpg";
        }

    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotosViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_design_photos, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {
        holder.bind(photoList.get(position), onClickListener);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onClick(Photo photo);
    }

}
