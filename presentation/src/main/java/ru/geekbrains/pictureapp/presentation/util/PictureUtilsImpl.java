package ru.geekbrains.pictureapp.presentation.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.R;

@Singleton
public final class PictureUtilsImpl implements PictureUtils {

    private final LayoutUtils layoutUtils;
    private final ContentUtils contentUtils;

    @Inject PictureUtilsImpl(final LayoutUtils layoutUtils, final ContentUtils contentUtils) {
        this.layoutUtils = layoutUtils;
        this.contentUtils = contentUtils;
    }

    @Override public Single<byte[]> getImageArray(final String url) {
        return Single.create(emitter -> Picasso.get().load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                emitter.onSuccess(outputStream.toByteArray());
            }

            @Override public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                emitter.onError(new RuntimeException("Can't load file"));
            }

            @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        }));
    }

    @Override public void loadOnlineImageIntoGridCell(final String url,
                                                      final ImageView imageView) {
        int photoSize = layoutUtils.getGridPhotoSize();
        Picasso.get()
                .load(url)
                .resize(photoSize, photoSize)
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);
    }

    @Override public void loadSavedImageIntoGridCell(final String filePath,
                                                     final ImageView imageView) {
        int photoSize = layoutUtils.getGridPhotoSize();
        Picasso.get()
                .load(contentUtils.getUri(filePath))
                .resize(photoSize, photoSize)
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);
    }

    @Override public void loadOnlineImageIntoFullView(final String url,
                                                      final ImageView imageView) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);
    }

    @Override public void loadSavedImageIntoFullView(final String filePath,
                                                     final ImageView imageView) {
        Picasso.get()
                .load(contentUtils.getUri(filePath))
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);
    }
}
