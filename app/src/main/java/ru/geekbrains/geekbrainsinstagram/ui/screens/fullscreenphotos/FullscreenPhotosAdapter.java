package ru.geekbrains.geekbrainsinstagram.ui.screens.fullscreenphotos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

public final class FullscreenPhotosAdapter extends RecyclerView.Adapter<FullscreenPhotosAdapter.FullscreenPhotoHolder> {

    interface FullscreenListener {

        void onDeleteClick(ViewPhotoModel photo);
    }

    private final PictureUtils pictureUtils;
    private final FullscreenListener fullscreenListener;

    private List<ViewPhotoModel> photos = Collections.emptyList();

    FullscreenPhotosAdapter(PictureUtils PictureUtils, FullscreenListener fullscreenListener) {
        this.pictureUtils = PictureUtils;
        this.fullscreenListener = fullscreenListener;
    }

    @NonNull
    @Override
    public FullscreenPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new FullscreenPhotoHolder(inflater.inflate(R.layout.item_favorite_photo,
                parent, false), pictureUtils, fullscreenListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FullscreenPhotoHolder holder, int position) {
        holder.bind(photos.get(position));
    }

    @Override
    public final int getItemCount() {
        return photos.size();
    }

    void updatePhotos(final List<ViewPhotoModel> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    void deletePhoto(final ViewPhotoModel photo) {
        notifyItemRemoved(photos.indexOf(photo));
        photos.remove(photo);
    }

    static final class FullscreenPhotoHolder extends RecyclerView.ViewHolder {

        private final ImageView photoImageView;
        private final PictureUtils pictureUtils;

        private ViewPhotoModel photo;

        FullscreenPhotoHolder(@NonNull View itemView, PictureUtils pictureUtils,
                              FullscreenListener fullscreenListener) {
            super(itemView);
            this.pictureUtils = pictureUtils;

            photoImageView = itemView.findViewById(R.id.iv_fullscreen_photo);
            itemView.findViewById(R.id.tv_delete_photo).setOnClickListener(
                    v -> fullscreenListener.onDeleteClick(photo));
        }

        void bind(final ViewPhotoModel photo) {
            this.photo = photo;
            pictureUtils.loadImageIntoGridImageView(photo, photoImageView);
        }
    }
}

