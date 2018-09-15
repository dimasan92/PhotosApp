package ru.geekbrains.geekbrainsinstagram.util;

import android.net.Uri;

import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

public interface ContentUtils {

    Uri getUriForPhoto(final ViewPhotoModel photo);
}
