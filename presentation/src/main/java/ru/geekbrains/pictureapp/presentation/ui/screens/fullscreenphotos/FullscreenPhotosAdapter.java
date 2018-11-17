package ru.geekbrains.pictureapp.presentation.ui.screens.fullscreenphotos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.pictureapp.domain.model.PhotoModel;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class FullscreenPhotosAdapter extends RecyclerView.Adapter<FullscreenPhotosAdapter.FullscreenPhotoHolder> {

    interface FullscreenListener {

        void onDeleteClick(PhotoModel photo);
    }

    private final PictureUtils pictureUtils;
    private final FullscreenListener fullscreenListener;

    private List<PhotoModel> photos = Collections.emptyList();

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

    void updatePhotos(final List<PhotoModel> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    void deletePhoto(final PhotoModel photo) {
        notifyItemRemoved(photos.indexOf(photo));
        photos.remove(photo);
    }

    static final class FullscreenPhotoHolder extends RecyclerView.ViewHolder {

        private final ImageView photoImageView;
        private final PictureUtils pictureUtils;

        private PhotoModel photo;

        FullscreenPhotoHolder(@NonNull View itemView, PictureUtils pictureUtils,
                              FullscreenListener fullscreenListener) {
            super(itemView);
            this.pictureUtils = pictureUtils;

            photoImageView = itemView.findViewById(R.id.iv_fullscreen_photo);
            itemView.findViewById(R.id.tv_delete_photo).setOnClickListener(
                    v -> fullscreenListener.onDeleteClick(photo));
        }

        void bind(final PhotoModel photo) {
            this.photo = photo;
            pictureUtils.loadSavedImageIntoGridCell(photo.getFilePath(), photoImageView);
        }
    }
}

