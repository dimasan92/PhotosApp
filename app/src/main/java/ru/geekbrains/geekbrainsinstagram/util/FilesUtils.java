package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.core.content.FileProvider;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@Singleton
public final class FilesUtils implements IFilesUtils {

    private static final String AUTHORITY = "ru.geekbrains.geekbrainsinstagram.fileprovider";

    private final Context appContext;

    @Inject
    FilesUtils(Context context) {
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
    public Uri getUriForPhoto(PresentPhotoModel photo) {
        return FileProvider.getUriForFile(appContext, AUTHORITY,
                getFileForPhoto(photo));
    }

    private File getFileForPhoto(PresentPhotoModel photo) {
        return new File(appContext.getFilesDir(), photo.getPhotoFileName());
    }
}
