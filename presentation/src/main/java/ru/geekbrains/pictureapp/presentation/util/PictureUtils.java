package ru.geekbrains.pictureapp.presentation.util;

import android.widget.ImageView;

import io.reactivex.Single;

public interface PictureUtils {

    Single<byte[]> getImageArray(final String url);

    void loadOnlineImageIntoGridCell(final String url, final ImageView imageView);

    void loadSavedImageIntoGridCell(final String filePath, final ImageView imageView);

    void loadOnlineImageIntoFullView(final String url, final ImageView imageView);

    void loadSavedImageIntoFullView(final String filePath, final ImageView imageView);
}
