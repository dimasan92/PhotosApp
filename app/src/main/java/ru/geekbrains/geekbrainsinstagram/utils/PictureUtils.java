package ru.geekbrains.geekbrainsinstagram.utils;

import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.geekbrains.geekbrainsinstagram.model.PhotoModel;

public final class PictureUtils implements IPictureUtils {

    @Override
    public void loadImageIntoImageView(PhotoModel photoModel, ImageView imageView) {
        Picasso.get()
                .load(Uri.parse(photoModel.getUri()))
                .into(imageView);
    }
}
