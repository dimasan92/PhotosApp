package ru.geekbrains.pictureapp.presentation.ui.screens.photos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseImagesAdapter;
import ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosListPresenter.PhotoRowView;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class PhotosAdapter extends BaseImagesAdapter<PhotosAdapter.Holder> {

    private final PhotosListPresenter presenter;
    private final PictureUtils pictureUtils;

    PhotosAdapter(final PhotosListPresenter presenter, final PictureUtils pictureUtils) {
        this.presenter = presenter;
        this.pictureUtils = pictureUtils;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new Holder(inflater.inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        presenter.bind(position, holder);
    }

    @Override
    public final int getItemCount() {
        return presenter.getCount();
    }

    final class Holder extends ViewHolder implements PhotoRowView {

        private final ImageView photoImageView;
        private final ImageView isFavoriteImageView;

        Holder(@NonNull View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.iv_photo);
            photoImageView.setOnClickListener(v -> presenter.onFullClick(getAdapterPosition()));

            isFavoriteImageView = itemView.findViewById(R.id.iv_is_favorite);
            isFavoriteImageView.setOnClickListener(v -> presenter.onFavoriteClick(getAdapterPosition()));

            itemView.findViewById(R.id.iv_io_action_photo).setOnClickListener(v ->
                    presenter.onDeleteClick(getAdapterPosition()));
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

