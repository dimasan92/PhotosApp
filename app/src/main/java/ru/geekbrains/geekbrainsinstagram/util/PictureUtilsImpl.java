package ru.geekbrains.geekbrainsinstagram.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

@Singleton
public final class PictureUtilsImpl implements PictureUtils {

    private final ContentUtils contentUtils;
    private final LayoutUtils layoutUtils;

    @Inject
    PictureUtilsImpl(ContentUtils contentUtils, LayoutUtils layoutUtils) {
        this.contentUtils = contentUtils;
        this.layoutUtils = layoutUtils;
    }

    @Override
    public void loadImageIntoGridImageView(ViewPhotoModel photo, ImageView imageView) {
        int photoSize = layoutUtils.getGridPhotoSize();
        Picasso.get()
                .load(contentUtils.getUriForPhoto(photo))
                .resize(photoSize, photoSize)
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);
    }

    @Override
    public void loadImageIntoFullImageView(ViewPhotoModel photo, ImageView imageView) {
        Picasso.get()
                .load(contentUtils.getUriForPhoto(photo))
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);
    }
}
