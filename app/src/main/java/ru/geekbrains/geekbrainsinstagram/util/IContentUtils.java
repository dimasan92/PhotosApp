package ru.geekbrains.geekbrainsinstagram.util;

import android.net.Uri;

import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IContentUtils {

    Uri getUriForPhoto(PresentPhotoModel photo);
}
