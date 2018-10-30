package ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter.ListView;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

import static androidx.recyclerview.widget.RecyclerView.*;
import static ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch.SavedSearchListPresenter.*;

public final class SavedSearchAdapter extends Adapter<SavedSearchAdapter.PhotoHolder>
        implements ListView {

    private final SavedSearchListPresenter presenter;
    private final PictureUtils pictureUtils;

    SavedSearchAdapter(final SavedSearchListPresenter presenter, final PictureUtils pictureUtils) {
        this.presenter = presenter;
        this.pictureUtils = pictureUtils;
    }

    @NonNull @Override public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PhotoHolder(inflater.inflate(R.layout.item_photo,
                parent, false));
    }

    @Override public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        presenter.bind(position, holder);
    }

    @Override public final int getItemCount() {
        return presenter.getCount();
    }

    @Override public void updatePhotos() {
        notifyDataSetChanged();
    }

    @Override public void updatePhoto(final int position) {
        notifyItemChanged(position);
    }

    @Override public void deletePhoto(int position) {
        notifyItemRemoved(position);
    }

    final class PhotoHolder extends ViewHolder implements SavedSearchRowView {

        private final ImageView photoImageView;
        private final ImageView isFavoriteImageView;
        private final ImageView ioActionImageView;

        PhotoHolder(@NonNull View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.iv_camera_photo);

            isFavoriteImageView = itemView.findViewById(R.id.iv_is_photo_favorite);
            isFavoriteImageView.setOnClickListener(v -> presenter.onFavoriteClick(getAdapterPosition()));

            ioActionImageView = itemView.findViewById(R.id.iv_io_action_photo);
            ioActionImageView.setOnClickListener(v -> presenter.onIoActionClick(getAdapterPosition()));
        }

        @Override public void loadImage(final String filePath) {
            pictureUtils.loadSavedImageIntoGridCell(filePath, photoImageView);
        }

        @Override public void setFavorite(final boolean isFavorite) {
            isFavoriteImageView.setImageResource(isFavorite ?
                    R.drawable.ic_star_filled_24dp :
                    R.drawable.ic_star_border_24dp);
        }
    }
}

