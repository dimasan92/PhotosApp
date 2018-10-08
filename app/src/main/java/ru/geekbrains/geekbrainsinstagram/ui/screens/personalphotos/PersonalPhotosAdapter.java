package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

public final class PersonalPhotosAdapter extends RecyclerView.Adapter<PersonalPhotosAdapter.PersonalPhotoHolder> {

    interface IPersonalPhotoListener {

        void onFavoritesClick(ViewPhotoModel photo);

        void onDeleteClick(ViewPhotoModel photo);

        void onDetailsClick(List<ViewPhotoModel> photos);
    }

    private final PictureUtils pictureUtils;
    private final IPersonalPhotoListener personalPhotoListener;

    private List<ViewPhotoModel> photos;

    PersonalPhotosAdapter(PictureUtils PictureUtils, IPersonalPhotoListener listener) {
        photos = new ArrayList<>();
        this.pictureUtils = PictureUtils;
        this.personalPhotoListener = listener;
    }

    @NonNull
    @Override
    public PersonalPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PersonalPhotoHolder(inflater.inflate(R.layout.item_personal_photo,
                parent, false), pictureUtils, personalPhotoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalPhotoHolder holder, int position) {
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

    void addPhoto(final ViewPhotoModel photo) {
        photos.add(photo);
        notifyItemChanged(photos.indexOf(photo));
    }

    void updatePhoto(final ViewPhotoModel photo) {
        int position = searchItemPosition(photo);
        if (position == -1) {
            return;
        }
        photos.set(position, photo);
        notifyItemChanged(position);
    }

    void deletePhoto(final ViewPhotoModel photo) {
        notifyItemRemoved(photos.indexOf(photo));
        photos.remove(photo);
    }

    private int searchItemPosition(final ViewPhotoModel photo) {
        int position = -1;
        for (int i = 0; i < photos.size(); i++) {
            if (photos.get(i).getId().equals(photo.getId())) {
                position = i;
                break;
            }
        }
        return position;
    }

    static final class PersonalPhotoHolder extends RecyclerView.ViewHolder {

        private final ImageView photoImageView;
        private final ImageView isFavoriteImageView;
        private final PictureUtils pictureUtils;

        private ViewPhotoModel photo;

        PersonalPhotoHolder(@NonNull View itemView, PictureUtils pictureUtils,
                            IPersonalPhotoListener personalPhotoListener) {
            super(itemView);
            this.pictureUtils = pictureUtils;

            photoImageView = itemView.findViewById(R.id.iv_personal_photo);
//            photoImageView.setOnClickListener(v -> personalPhotoListener.onDetailsClick(photo));

            isFavoriteImageView = itemView.findViewById(R.id.iv_is_photo_favorite);
            isFavoriteImageView.setOnClickListener((v ->
                    personalPhotoListener.onFavoritesClick(photo)));

            ImageView deleteImageView = itemView.findViewById(R.id.iv_delete_favorite);
            deleteImageView.setOnClickListener(v -> personalPhotoListener.onDeleteClick(photo));
        }

        void bind(final ViewPhotoModel photo) {
            this.photo = photo;
            pictureUtils.loadImageIntoGridImageView(photo, photoImageView);
            isFavoriteImageView.setImageResource(photo.isFavorite() ?
                    R.drawable.ic_star_filled_24dp :
                    R.drawable.ic_star_border_24dp);
        }
    }
}

