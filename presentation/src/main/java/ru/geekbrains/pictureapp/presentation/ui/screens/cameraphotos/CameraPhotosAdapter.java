package ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BasePhotosAdapter;
import ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos.CameraPhotoListPresenter.CameraPhotoRowView;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class CameraPhotosAdapter extends BasePhotosAdapter<CameraPhotosAdapter.PhotoHolder> {

    private final CameraPhotoListPresenter presenter;
    private final PictureUtils pictureUtils;

    CameraPhotosAdapter(final CameraPhotoListPresenter presenter, final PictureUtils pictureUtils) {
        this.presenter = presenter;
        this.pictureUtils = pictureUtils;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PhotoHolder(inflater.inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        presenter.bind(position, holder);
    }

    @Override
    public final int getItemCount() {
        return presenter.getCount();
    }

    final class PhotoHolder extends ViewHolder implements CameraPhotoRowView {

        private final ImageView photoImageView;
        private final ImageView isFavoriteImageView;

        PhotoHolder(@NonNull View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.iv_camera_photo);

            isFavoriteImageView = itemView.findViewById(R.id.iv_is_photo_favorite);
            isFavoriteImageView.setOnClickListener(v -> presenter.onFavoriteClick(getAdapterPosition()));

            itemView.findViewById(R.id.iv_io_action_photo).setOnClickListener(v ->
                    presenter.onDeleteClick(getAdapterPosition()));
        }

        @Override
        public void loadImage(final String filePath) {
            pictureUtils.loadSavedImageIntoGridCell(filePath, photoImageView);
        }

        @Override
        public void setFavorite(final boolean isFavorite) {
            isFavoriteImageView.setImageResource(isFavorite ?
                    R.drawable.ic_star_filled_24dp :
                    R.drawable.ic_star_border_24dp);
        }
    }
}

