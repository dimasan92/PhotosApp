package ru.geekbrains.geekbrainsinstagram.util;

import android.widget.ImageView;

import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

public interface PictureUtils {

    void loadImageIntoGridImageView(final ViewPhotoModel photo, final ImageView imageView);

    void loadImageIntoFullImageView(final ViewPhotoModel photo, final ImageView imageView);
}
