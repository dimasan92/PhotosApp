package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

public final class CameraPhotosAdapter extends RecyclerView.Adapter<CameraPhotosAdapter.PhotoHolder> {

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PhotoHolder(inflater.inflate(R.layout.item_personal_photo,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
    }

    @Override
    public final int getItemCount() {
        return 0;
    }

    void updatePhotos() {
        notifyDataSetChanged();
    }

    void updatePhoto(int position) {
        notifyItemChanged(position);
    }

    void deletePhoto(int position) {
        notifyItemRemoved(position);
    }

    static final class PhotoHolder extends RecyclerView.ViewHolder {

        PhotoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

