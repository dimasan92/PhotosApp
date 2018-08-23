package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoViewModel;
import ru.geekbrains.geekbrainsinstagram.utils.PictureUtils;

public final class CameraPhotoAdapter extends RecyclerView.Adapter<CameraPhotoAdapter.PhotoHolder> {

    private final PictureUtils pictureUtils;
    private List<InnerStoragePhotoViewModel> photos = Collections.emptyList();

    private final Subject<InnerStoragePhotoViewModel> onFavoritesItemClickObservable = BehaviorSubject.create();
    private final Subject<InnerStoragePhotoViewModel> onLongItemClickObservable = BehaviorSubject.create();

    public CameraPhotoAdapter(PictureUtils pictureUtils) {
        this.pictureUtils = pictureUtils;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PhotoHolder(inflater.inflate(R.layout.item_camera_gallery,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        holder.bind(photos.get(position));
    }

    void setPictures(List<InnerStoragePhotoViewModel> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @Override
    public final int getItemCount() {
        return photos.size();
    }

    Observable<InnerStoragePhotoViewModel> onFavoritesClick() {
        return onFavoritesItemClickObservable.doOnNext(this::changeFavoritesStatus);
    }

    Observable<InnerStoragePhotoViewModel> onDeleteClick() {
        return onLongItemClickObservable;
    }

    private void changeFavoritesStatus(final InnerStoragePhotoViewModel photoModel) {
        final int position = photos.indexOf(photoModel);
        photoModel.setFavorite(!photoModel.isFavorite());
        notifyItemChanged(position);
    }

    class PhotoHolder extends RecyclerView.ViewHolder {

        private final ImageView photoImageView;
        private final ImageView favoritesImageView;

        private InnerStoragePhotoViewModel photoModel;

        PhotoHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.camera_photo_image);
            favoritesImageView = itemView.findViewById(R.id.favorite_photo);

            itemView.setOnLongClickListener(v -> {
                onLongItemClickObservable.onNext(photoModel);
                return true;
            });

            favoritesImageView.setOnClickListener(v -> onFavoritesItemClickObservable.onNext(photoModel));
        }

        void bind(final InnerStoragePhotoViewModel model) {
            this.photoModel = model;
            pictureUtils.loadImageIntoImageView(photoImageView, model);
            favoritesImageView.setImageResource(model.isFavorite() ?
                    R.drawable.ic_star_filled_24dp :
                    R.drawable.ic_star_border_24dp);
        }
    }
}

