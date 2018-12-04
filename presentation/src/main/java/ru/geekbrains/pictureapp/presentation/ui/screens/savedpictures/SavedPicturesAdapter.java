package ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseImagesAdapter;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

import static androidx.recyclerview.widget.RecyclerView.ViewHolder;
import static ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures.SavedPicturesListPresenter.SavedPicturesRowView;

public final class SavedPicturesAdapter extends BaseImagesAdapter<SavedPicturesAdapter.PhotoHolder> {

    private final SavedPicturesListPresenter presenter;
    private final PictureUtils pictureUtils;

    SavedPicturesAdapter(final SavedPicturesListPresenter presenter, final PictureUtils pictureUtils) {
        this.presenter = presenter;
        this.pictureUtils = pictureUtils;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PhotoHolder(inflater.inflate(R.layout.item_photo,
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

    final class PhotoHolder extends ViewHolder implements SavedPicturesRowView {

        private final ImageView photoImageView;
        private final ImageView isFavoriteImageView;
        private final ImageView ioActionImageView;

        PhotoHolder(@NonNull View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.iv_photo);
            photoImageView.setOnClickListener(v -> presenter.onFullClick(getAdapterPosition()));

            isFavoriteImageView = itemView.findViewById(R.id.iv_is_favorite);
            isFavoriteImageView.setOnClickListener(v -> presenter.onFavoriteClick(getAdapterPosition()));

            ioActionImageView = itemView.findViewById(R.id.iv_io_action_photo);
            ioActionImageView.setOnClickListener(v -> presenter.onIoActionClick(getAdapterPosition()));
        }

        @Override
        public void loadImage(final ImageModel imageModel) {
            pictureUtils.loadImageIntoGridCell(imageModel, photoImageView);
        }

        @Override
        public void setFavorite(final boolean isFavorite) {
            isFavoriteImageView.setImageResource(isFavorite ?
                    R.drawable.ic_star_filled_24dp :
                    R.drawable.ic_star_border_24dp);
        }
    }
}

