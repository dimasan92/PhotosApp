package ru.geekbrains.geekbrainsinstagram.utils;

import android.net.Uri;

import java.io.File;

import ru.geekbrains.geekbrainsinstagram.model.PhotoModel;

public interface IFilesUtils {

    boolean isCatalogAvailable();

    Uri getUriForFile(File file);

    File getFileForPhoto(PhotoModel model);
}
