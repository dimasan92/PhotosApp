package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

public final class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesHolder> {

    interface IFavoriteListener {

        void onDeleteFromFavoritesClick(ViewPhotoModel photo);

        void onDeleteFromDeviceClick(ViewPhotoModel photo);

        void onDetailsClick(ViewPhotoModel photo);
    }

    private final PictureUtils pictureUtils;
    private final IFavoriteListener favoriteListener;

    private List<ViewPhotoModel> photos;

    FavoritesAdapter(PictureUtils PictureUtils, IFavoriteListener favoriteListener) {
        photos = new ArrayList<>();
        this.pictureUtils = PictureUtils;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public FavoritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new FavoritesHolder(inflater.inflate(R.layout.item_favorite_photo,
                parent, false), pictureUtils, favoriteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesHolder holder, int position) {
        holder.bind(photos.get(position));
    }

    @Override
    public final int getItemCount() {
        return photos.size();
    }

    void updatePhotos(final List<ViewPhotoModel> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    void deletePhoto(final ViewPhotoModel photo) {
        notifyItemRemoved(photos.indexOf(photo));
        photos.remove(photo);
    }

    static final class FavoritesHolder extends RecyclerView.ViewHolder {

        private final ImageView photoImageView;
        private final ImageView moreImageView;
        private final PictureUtils pictureUtils;

        private ViewPhotoModel photo;

        FavoritesHolder(@NonNull View itemView, PictureUtils pictureUtils,
                        IFavoriteListener favoriteListener) {
            super(itemView);
            this.pictureUtils = pictureUtils;

            photoImageView = itemView.findViewById(R.id.iv_favorite_photo);
            photoImageView.setOnClickListener(v -> favoriteListener.onDetailsClick(photo));

            moreImageView = itemView.findViewById(R.id.iv_more_menu);
            moreImageView.setOnClickListener(v -> showMoreMenu(v, favoriteListener));
        }

        void bind(final ViewPhotoModel photo) {
            this.photo = photo;
            pictureUtils.loadImageIntoGridImageView(photo, photoImageView);
        }

        private void showMoreMenu(View view, IFavoriteListener listener) {
            PopupMenu menu = new PopupMenu(view.getContext(), view);
            menu.inflate(R.menu.more_favorite_menu);

            menu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.delete_from_favorites_action:
                        listener.onDeleteFromFavoritesClick(photo);
                        return true;
                    case R.id.delete_from_device_action:
                        listener.onDeleteFromDeviceClick(photo);
                        return true;
                    default:
                        return false;
                }
            });

            menu.show();
        }
    }
}

