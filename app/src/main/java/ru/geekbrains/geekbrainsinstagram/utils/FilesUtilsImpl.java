package ru.geekbrains.geekbrainsinstagram.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import androidx.core.content.FileProvider;
import ru.geekbrains.geekbrainsinstagram.ui.model.InnerStoragePhotoViewModel;

public final class FilesUtilsImpl implements FilesUtils {

    private static final String AUTHORITY = "ru.geekbrains.geekbrainsinstagram.fileprovider";

    private final Context appContext;

    public FilesUtilsImpl(Context context) {
        appContext = context;
    }

    @Override
    public boolean isCatalogAvailable() {
        if (!appContext.getFilesDir().exists()) {
            return appContext.getFilesDir().mkdir();
        }
        return true;
    }

    @Override
    public Uri getUriForFile(File file) {
        return FileProvider.getUriForFile(appContext, AUTHORITY, file);
    }

    @Override
    public File getInnerPhotoFile(InnerStoragePhotoViewModel model) {
        return new File(appContext.getFilesDir(), model.getPhotoFileName());
    }
}
