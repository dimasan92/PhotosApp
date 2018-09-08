package ru.geekbrains.geekbrainsinstagram.util;

import android.net.Uri;

import java.io.File;

import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IContentUtils {

    boolean isCatalogAvailable();

    Uri getUriForPhoto(PresentPhotoModel photo);
}
