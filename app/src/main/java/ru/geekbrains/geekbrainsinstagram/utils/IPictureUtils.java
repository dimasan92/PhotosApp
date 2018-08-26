package ru.geekbrains.geekbrainsinstagram.utils;

import android.widget.ImageView;

import ru.geekbrains.geekbrainsinstagram.ui.model.PhotoModel;

public interface IPictureUtils {

    void loadImageIntoImageView(PhotoModel photoModel, ImageView imageView);
}
