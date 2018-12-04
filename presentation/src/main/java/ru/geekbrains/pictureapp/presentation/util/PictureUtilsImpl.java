package ru.geekbrains.pictureapp.presentation.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.domain.model.ImageModel;
import ru.geekbrains.pictureapp.domain.util.Constants;

@Singleton
public final class PictureUtilsImpl implements PictureUtils {

    private final LayoutUtils layoutUtils;
    private final ContentUtils contentUtils;

    @Inject
    PictureUtilsImpl(final LayoutUtils layoutUtils, final ContentUtils contentUtils) {
        this.layoutUtils = layoutUtils;
        this.contentUtils = contentUtils;
    }

    @Override
    public Single<byte[]> getImageArray(final String url) {
        return Single.create(emitter -> Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                emitter.onSuccess(outputStream.toByteArray());
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                emitter.onError(new RuntimeException("Can't load file"));
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        }));
    }

    @Override
    public void loadImageIntoGridCell(final ImageModel imageModel, final ImageView imageView) {
        final int photoSize = layoutUtils.getGridPhotoSize();
        final String filePath = imageModel.getFilePath();
        final String url = imageModel.getSmallUrl();
        getCreator(filePath, url).resize(photoSize, photoSize).into(imageView);
    }

    @Override
    public void loadImageIntoFullView(final ImageModel imageModel, final ImageView imageView) {
        final String filePath = imageModel.getFilePath();
        final String url = imageModel.getRegularUrl();
        getCreator(filePath, url).into(imageView);
    }

    private RequestCreator getCreator(final String filePath, final String url) {
        final Picasso picasso = Picasso.get();
        final RequestCreator requestCreator;

        if (filePath.equals(Constants.EMPTY_STRING)) {
            if (url.equals(Constants.EMPTY_STRING)) {
                requestCreator = picasso.load(R.drawable.ic_error_black_24dp);
            } else {
                requestCreator = picasso.load(url).placeholder(R.drawable.ic_image_black_24dp);
            }
        } else {
            requestCreator = picasso.load(contentUtils.getUri(filePath));
        }
        requestCreator.error(R.drawable.ic_error_black_24dp);
        return requestCreator;
    }
}
