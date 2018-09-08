package ru.geekbrains.geekbrainsinstagram.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.core.content.FileProvider;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@Singleton
public final class ContentUtils implements IContentUtils {

    private final String authority;
    private final Context appContext;

    @Inject
    ContentUtils(Context context) {
        appContext = context;
        authority = context.getResources().getString(R.string.authority);
    }

    @Override
    public Uri getUriForPhoto(PresentPhotoModel photo) {
        return FileProvider.getUriForFile(appContext, authority,
                getFileForPhoto(photo));
    }

    private File getFileForPhoto(PresentPhotoModel photo) {
        return new File(appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), photo.getPhotoFileName());
    }
}
