package ru.geekbrains.geekbrainsinstagram.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoModel;

public final class PictureUtilsImpl implements PictureUtils {

    @Override
    public void loadImageIntoImageView(ImageView imageView, InnerStoragePhotoModel model) {
        Picasso.get().load(model.getUri()).into(imageView);
    }
}
