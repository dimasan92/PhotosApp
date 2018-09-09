package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.util.IPictureUtils;

public final class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesHolder> {

    interface IFavoriteListener {

        void onDeleteFromFavoritesClick(PresentPhotoModel photo);

        void onDeleteFromDeviceClick(PresentPhotoModel photo);
    }

    private final IPictureUtils pictureUtils;
    private final IFavoriteListener favoriteListener;

    private List<PresentPhotoModel> photos = Collections.emptyList();

    FavoritesAdapter(IPictureUtils IPictureUtils, IFavoriteListener favoriteListener) {
        this.pictureUtils = IPictureUtils;
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

    void updatePhotos(final List<PresentPhotoModel> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    void deletePhoto(final PresentPhotoModel photo) {
        notifyItemRemoved(photos.indexOf(photo));
        photos.remove(photo);
    }

    static final class FavoritesHolder extends RecyclerView.ViewHolder {

        private final ImageView photoImageView;
        private final ImageView moreImageView;
        private final IFavoriteListener favoriteListener;
        private final IPictureUtils pictureUtils;

        private PresentPhotoModel photo;

        FavoritesHolder(@NonNull View itemView, IPictureUtils pictureUtils,
                        IFavoriteListener favoriteListener) {
            super(itemView);
            this.pictureUtils = pictureUtils;
            this.favoriteListener = favoriteListener;

            photoImageView = itemView.findViewById(R.id.iv_favorite_photo);
            moreImageView = itemView.findViewById(R.id.iv_more_menu);

            moreImageView.setOnClickListener(this::showMoreMenu);
        }

        void bind(final PresentPhotoModel photo) {
            this.photo = photo;
            pictureUtils.loadImageIntoImageView(photo, photoImageView);
        }

        private void showMoreMenu(View view) {
            PopupMenu menu = new PopupMenu(view.getContext(), view);
            menu.inflate(R.menu.more_favorite_menu);

            menu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.delete_from_favorites_action:
                        favoriteListener.onDeleteFromFavoritesClick(photo);
                        return true;
                    case R.id.delete_from_device_action:
                        favoriteListener.onDeleteFromDeviceClick(photo);
                        return true;
                    default:
                        return false;
                }
            });

            menu.show();
        }
    }
}

