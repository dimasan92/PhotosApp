package ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseImagesAdapter;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures.OnlinePicturesListPresenter.OnlineSearchRowView;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

public final class OnlinePicturesAdapter extends BaseImagesAdapter<OnlinePicturesAdapter.Holder> {

    private final OnlinePicturesListPresenter presenter;
    private final PictureUtils pictureUtils;

    OnlinePicturesAdapter(final OnlinePicturesListPresenter presenter, final PictureUtils pictureUtils) {
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

    final class Holder extends ViewHolder implements OnlineSearchRowView {

        private final ImageView photoImageView;
        private final ImageView isFavoriteImageView;
        private final ImageView ioActionImageView;

        Holder(@NonNull View itemView) {
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
        public void favoriteVisibility(boolean isVisible) {
            if (isVisible) {
                isFavoriteImageView.setVisibility(View.VISIBLE);
            } else {
                isFavoriteImageView.setVisibility(View.GONE);
            }
        }

        @Override
        public void setFavorite(final boolean isFavorite) {
            isFavoriteImageView.setImageResource(isFavorite ?
                    R.drawable.ic_star_filled_24dp :
                    R.drawable.ic_star_border_24dp);
        }

        @Override
        public void setSaving(boolean isSaving) {
            ioActionImageView.setImageResource(isSaving ?
                    R.drawable.ic_delete_white_24dp :
                    R.drawable.ic_save_white_24dp);
        }
    }
}

