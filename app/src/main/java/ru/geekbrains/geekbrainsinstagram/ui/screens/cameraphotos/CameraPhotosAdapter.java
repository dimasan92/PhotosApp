package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

public final class CameraPhotosAdapter extends RecyclerView.Adapter<CameraPhotosAdapter.PhotoHolder>
        implements CameraPhotoListPresenter.CameraPhotosListView {

    private final CameraPhotoListPresenter presenter;
    private final PictureUtils pictureUtils;

    CameraPhotosAdapter(CameraPhotoListPresenter presenter, PictureUtils pictureUtils) {
        this.presenter = presenter;
        this.pictureUtils = pictureUtils;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PhotoHolder(inflater.inflate(R.layout.item_camera_photo,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        presenter.bind(position, holder);
    }

    @Override
    public final int getItemCount() {
        return presenter.getCount();
    }

    @Override
    public void updatePhotos() {
        notifyDataSetChanged();
    }

    @Override
    public void updatePhoto(final int position) {
        notifyItemChanged(position);
    }

    @Override
    public void deletePhoto(final int position) {
        notifyItemRemoved(position);
    }

    final class PhotoHolder extends RecyclerView.ViewHolder implements CameraPhotoListPresenter.CameraPhotoView {

        private final ImageView photoImageView;
        private final ImageView isFavoriteImageView;

        PhotoHolder(@NonNull View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.iv_camera_photo);

            isFavoriteImageView = itemView.findViewById(R.id.iv_is_photo_favorite);
            isFavoriteImageView.setOnClickListener(v -> presenter.onFavoriteClick(getAdapterPosition()));

            itemView.findViewById(R.id.iv_delete_photo).setOnClickListener(v ->
                    presenter.onDeleteClick(getAdapterPosition()));
        }

        @Override
        public void loadImage(final ViewPhotoModel photoModel) {
            pictureUtils.loadImageIntoGridImageView(photoModel, photoImageView);
        }

        @Override
        public void setFavorite(final boolean isFavorite) {
            isFavoriteImageView.setImageResource(isFavorite ?
                    R.drawable.ic_star_filled_24dp :
                    R.drawable.ic_star_border_24dp);
        }
    }
}

