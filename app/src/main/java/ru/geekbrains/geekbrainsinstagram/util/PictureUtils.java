package ru.geekbrains.geekbrainsinstagram.util;

import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@Singleton
public final class PictureUtils implements IPictureUtils {

    @Inject
    PictureUtils() {
    }

    @Override
    public void loadImageIntoImageView(PresentPhotoModel photoModel, ImageView imageView) {
        Picasso.get()
                .load(Uri.parse(photoModel.getUri()))
                .into(imageView);
    }
}
