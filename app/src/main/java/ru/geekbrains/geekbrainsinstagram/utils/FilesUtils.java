package ru.geekbrains.geekbrainsinstagram.utils;

import android.net.Uri;

import java.io.File;

import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoModel;

public interface FilesUtils {

    boolean isCatalogAvailable();

    Uri getUriForFile(File file);

    File getInnerPhotoFile(InnerStoragePhotoModel model);
}
