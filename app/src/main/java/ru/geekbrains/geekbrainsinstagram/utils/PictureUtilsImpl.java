package ru.geekbrains.geekbrainsinstagram.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoViewModel;

public final class PictureUtilsImpl implements PictureUtils {

    @Override
    public void loadImageIntoImageView(ImageView imageView, InnerStoragePhotoViewModel model) {
        Picasso.get().load(model.getUri()).into(imageView);
    }
}
