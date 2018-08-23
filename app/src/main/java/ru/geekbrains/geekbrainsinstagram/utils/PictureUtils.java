package ru.geekbrains.geekbrainsinstagram.utils;

import android.widget.ImageView;

import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoViewModel;

public interface PictureUtils {

    void loadImageIntoImageView(ImageView imageView, InnerStoragePhotoViewModel model);
}
