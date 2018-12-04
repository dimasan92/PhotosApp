package ru.geekbrains.pictureapp.presentation.util;

import android.widget.ImageView;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.ImageModel;

public interface PictureUtils {

    Single<byte[]> getImageArray(final String url);

    void loadImageIntoGridCell(final ImageModel imageModel, final ImageView imageView);

    void loadImageIntoFullView(final ImageModel imageModel, final ImageView imageView);
}
