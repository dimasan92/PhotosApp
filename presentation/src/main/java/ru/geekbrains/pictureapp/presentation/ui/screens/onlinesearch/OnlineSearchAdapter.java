package ru.geekbrains.pictureapp.presentation.ui.screens.onlinesearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinesearch.OnlineSearchListPresenter.OnlineSearchRowView;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class OnlineSearchAdapter extends Adapter<OnlineSearchAdapter.PhotoHolder>
        implements BaseListPresenter.ListView {

    private final OnlineSearchListPresenter presenter;
    private final PictureUtils pictureUtils;

    OnlineSearchAdapter(final OnlineSearchListPresenter presenter, final PictureUtils pictureUtils) {
        this.presenter = presenter;
        this.pictureUtils = pictureUtils;
    }

    @NonNull @Override public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PhotoHolder(inflater.inflate(R.layout.item_photo, parent, false));
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

    @Override public void deletePhoto(final int position) {
        notifyItemRemoved(position);
    }

    final class PhotoHolder extends ViewHolder implements OnlineSearchRowView {

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

        @Override public void loadImage(final String url) {
            pictureUtils.loadOnlineImageIntoGridCell(url, photoImageView);
        }

        @Override public void favoriteVisibility(boolean isVisible) {
            if (isVisible) {
                isFavoriteImageView.setVisibility(View.VISIBLE);
            } else {
                isFavoriteImageView.setVisibility(View.GONE);
            }
        }

        @Override public void setFavorite(final boolean isFavorite) {
            isFavoriteImageView.setImageResource(isFavorite ?
                    R.drawable.ic_star_filled_24dp :
                    R.drawable.ic_star_border_24dp);
        }

        @Override public void setSaving(boolean isSaving) {
            ioActionImageView.setImageResource(isSaving ?
                    R.drawable.ic_delete_white_24dp :
                    R.drawable.ic_save_white_24dp);
        }
    }
}

