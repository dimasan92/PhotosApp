package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesListPresenter.FavoriteView;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesListPresenter.FavoritesListView;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

final class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesHolder>
        implements FavoritesListView {

    private final PictureUtils pictureUtils;
    private final FavoritesListPresenter presenter;

    FavoritesAdapter(FavoritesListPresenter presenter, PictureUtils PictureUtils) {
        this.pictureUtils = PictureUtils;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public FavoritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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

    @Override
    public void updatePhotos() {
        notifyDataSetChanged();
    }

    @Override
    public void deletePhoto(final int position) {
        notifyItemRemoved(position);
    }

    final class FavoritesHolder extends RecyclerView.ViewHolder implements FavoriteView {

        private final ImageView photoImageView;
        private final ImageView moreImageView;

        FavoritesHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.iv_favorite_photo);

            moreImageView = itemView.findViewById(R.id.iv_more_menu);
            moreImageView.setOnClickListener(this::showMoreMenu);
        }

        @Override
        public void loadImage(final ViewPhotoModel photoModel) {
            pictureUtils.loadImageIntoGridImageView(photoModel, photoImageView);
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
    }
}

