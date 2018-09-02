package ru.geekbrains.geekbrainsinstagram.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@Singleton
public final class PictureUtils implements IPictureUtils {

    private final IFilesUtils filesUtils;

    @Inject
    PictureUtils(IFilesUtils filesUtils) {
        this.filesUtils = filesUtils;
    }

    @Override
    public void loadImageIntoImageView(PresentPhotoModel photo, ImageView imageView) {
        Picasso.get()
                .load(filesUtils.getUriForPhoto(photo))
                .into(imageView);
    }
}
