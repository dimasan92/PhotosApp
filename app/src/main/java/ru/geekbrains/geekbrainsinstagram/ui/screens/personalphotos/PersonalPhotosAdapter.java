package ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.util.IPictureUtils;

public final class PersonalPhotosAdapter extends RecyclerView.Adapter<PersonalPhotosAdapter.PersonalPhotoHolder> {

    interface IPersonalPhotoListener {

        void onFavoritesClick(PresentPhotoModel photo);

        void onDeleteClick(PresentPhotoModel photo);
    }

    private final IPictureUtils pictureUtils;
    private final IPersonalPhotoListener personalPhotoListener;

    private List<PresentPhotoModel> photos = Collections.emptyList();

    PersonalPhotosAdapter(IPictureUtils IPictureUtils, IPersonalPhotoListener listener) {
        this.pictureUtils = IPictureUtils;
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

    void updatePhotos(List<PresentPhotoModel> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    void addPhoto(PresentPhotoModel photoModel) {
        photos.add(photoModel);
        notifyItemChanged(photos.indexOf(photoModel));
    }

    void updatePhoto(PresentPhotoModel photoModel) {
        notifyItemChanged(photos.indexOf(photoModel));
    }

    void deletePhoto(PresentPhotoModel photoModel) {
        notifyItemRemoved(photos.indexOf(photoModel));
        photos.remove(photoModel);
    }

    static final class PersonalPhotoHolder extends RecyclerView.ViewHolder {

        private final ImageView photoImageView;
        private final ImageView isFavoritesImageView;
        private final IPictureUtils pictureUtils;

        private PresentPhotoModel photo;

        PersonalPhotoHolder(@NonNull View itemView, IPictureUtils pictureUtils,
                            IPersonalPhotoListener personalPhotoListener) {
            super(itemView);
            this.pictureUtils = pictureUtils;

            photoImageView = itemView.findViewById(R.id.iv_personal_photo);
            isFavoritesImageView = itemView.findViewById(R.id.iv_is_photo_favorite);

            itemView.setOnLongClickListener(v -> {
                personalPhotoListener.onDeleteClick(photo);
                return true;
            });

            isFavoritesImageView.setOnClickListener((v ->
                    personalPhotoListener.onFavoritesClick(photo)));
        }

        void bind(final PresentPhotoModel photoModel) {
            this.photo = photoModel;
            pictureUtils.loadImageIntoImageView(photoModel, photoImageView);
            isFavoritesImageView.setImageResource(photoModel.isFavorite() ?
                    R.drawable.ic_star_filled_24dp :
                    R.drawable.ic_star_border_24dp);
        }
    }
}

