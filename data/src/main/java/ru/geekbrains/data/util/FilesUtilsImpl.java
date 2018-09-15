package ru.geekbrains.data.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class FilesUtilsImpl implements FilesUtils {

    private final File mainPhotoDirectory;

    @Inject
    FilesUtilsImpl(Context context) {
        mainPhotoDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public String[] getPhotosIdsFromDevice() {
        File[] files = mainPhotoDirectory.listFiles();
        String[] ids = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            ids[i] = files[i].getName();
        }
        return ids;
    }

    @Override
    public boolean deletePhotoFromDevice(final String photoId) {
        return getFileFromId(photoId).delete();
    }

    private File getFileFromId(final String id) {
        return new File(mainPhotoDirectory, id);
    }
}
