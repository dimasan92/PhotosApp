package ru.geekbrains.geekbrainsinstagram.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@Singleton
public final class PictureUtils implements IPictureUtils {

    private final IContentUtils filesUtils;
    private final ILayoutUtils layoutUtils;

    @Inject
    PictureUtils(IContentUtils filesUtils, ILayoutUtils layoutUtils) {
        this.filesUtils = filesUtils;
        this.layoutUtils = layoutUtils;
    }

    @Override
    public void loadImageIntoImageView(PresentPhotoModel photo, ImageView imageView) {

        int photoSize = layoutUtils.getPhotoSize();
        Picasso.get()
                .load(filesUtils.getUriForPhoto(photo))
                .resize(photoSize, photoSize)
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);
    }
}
