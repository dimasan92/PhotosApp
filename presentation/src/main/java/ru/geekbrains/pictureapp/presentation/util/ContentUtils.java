package ru.geekbrains.pictureapp.presentation.util;

import android.net.Uri;

public interface ContentUtils {

    Uri getUri(final String filePath);

    String getExtensionForUnsplashPhoto(final String url);
}
