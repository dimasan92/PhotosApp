package ru.geekbrains.data.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class FilesUtils implements IFilesUtils {

    private final File mainPhotoDirectory;

    @Inject
    FilesUtils(Context appContext) {
        mainPhotoDirectory = appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public String[] getPhotosIdsFromDevice() {
        File[] files = mainPhotoDirectory.listFiles();
        String[] ids = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            ids[i] = getIdFromFile(files[i]);
        }
        return ids;
    }

    @Override
    public boolean deletePhotoFromDevice(String photoId) {
        return getFileFromId(photoId).delete();
    }

    private String getIdFromFile(File file) {
        return file.getName().split("_")[1];
    }

    private File getFileFromId(String id) {
        return new File(mainPhotoDirectory, "PHOTO_" + id);
    }
}
