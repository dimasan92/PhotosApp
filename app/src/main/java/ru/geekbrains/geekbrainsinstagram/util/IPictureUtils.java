package ru.geekbrains.geekbrainsinstagram.util;

import android.widget.ImageView;

import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IPictureUtils {

    void loadImageIntoImageViewGrid(PresentPhotoModel photo, ImageView imageView);

    void loadImageIntoImageViewFull(PresentPhotoModel photo, ImageView imageView);
}
