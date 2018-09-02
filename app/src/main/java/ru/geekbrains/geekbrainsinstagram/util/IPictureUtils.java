package ru.geekbrains.geekbrainsinstagram.util;

import android.widget.ImageView;

import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IPictureUtils {

    void loadImageIntoImageView(PresentPhotoModel photoModel, ImageView imageView);
}
