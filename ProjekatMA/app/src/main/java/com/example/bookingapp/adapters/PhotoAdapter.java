package com.example.bookingapp.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bookingapp.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Uri> photoUris;
    private OnPhotoRemoveListener onPhotoRemoveListener;

    public interface OnPhotoRemoveListener {
        void onRemove(int position);
    }

    public PhotoAdapter(List<Uri> photoUris, OnPhotoRemoveListener onPhotoRemoveListener) {
        this.photoUris = photoUris;
        this.onPhotoRemoveListener = onPhotoRemoveListener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Uri uri = photoUris.get(position);
        holder.imageView.setImageURI(uri);
        holder.buttonRemove.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onPhotoRemoveListener.onRemove(adapterPosition);
            }
        });
//        holder.buttonRemove.setOnClickListener(v -> onPhotoRemoveListener.onRemove(position));
    }

    @Override
    public int getItemCount() {
        return photoUris.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button buttonRemove;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }
}
