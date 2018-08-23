package ru.geekbrains.geekbrainsinstagram.utils;

import android.widget.ImageView;

import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoModel;

public interface PictureUtils {

    void loadImageIntoImageView(ImageView imageView, InnerStoragePhotoModel model);
}
