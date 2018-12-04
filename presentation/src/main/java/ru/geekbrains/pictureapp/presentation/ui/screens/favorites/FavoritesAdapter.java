package ru.geekbrains.pictureapp.presentation.ui.screens.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseImagesAdapter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.RowView;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;

final class FavoritesAdapter extends BaseImagesAdapter<FavoritesAdapter.FavoritesHolder> {

    private final PictureUtils pictureUtils;
    private final FavoritesListPresenter presenter;

    FavoritesAdapter(final FavoritesListPresenter presenter, final PictureUtils PictureUtils) {
        this.pictureUtils = PictureUtils;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public FavoritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new FavoritesHolder(inflater.inflate(R.layout.item_favorite_photo,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesHolder holder, int position) {
        presenter.bind(position, holder);
    }

    @Override
    public final int getItemCount() {
        return presenter.getCount();
    }

    final class FavoritesHolder extends ViewHolder implements RowView {

        private final ImageView photoImageView;
        private final ImageView moreImageView;

        FavoritesHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.iv_favorite_photo);
            photoImageView.setOnClickListener(v -> presenter.onFullClick(getAdapterPosition()));

            moreImageView = itemView.findViewById(R.id.iv_more_menu);
            moreImageView.setOnClickListener(this::showMoreMenu);
        }

        private void showMoreMenu(final View view) {
            final PopupMenu menu = new PopupMenu(view.getContext(), view);
            menu.inflate(R.menu.more_favorite_menu);

            menu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.delete_from_favorites_action:
                        presenter.onDeleteFromFavoritesClick(getAdapterPosition());
                        return true;
                    case R.id.delete_from_device_action:
                        presenter.onDeleteFromDeviceClick(getAdapterPosition());
                        return true;
                    default:
                        return false;
                }
            });
            menu.show();
        }

        @Override
        public void loadImage(final ImageModel imageModel) {
            pictureUtils.loadImageIntoGridCell(imageModel, photoImageView);
        }
    }
}

