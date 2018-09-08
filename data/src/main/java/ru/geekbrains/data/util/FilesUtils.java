package ru.geekbrains.data.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FilesUtils implements IFilesUtils {

    private final File mainPhotoDirectory;

    @Inject
    FilesUtils(Context appContext) {
        mainPhotoDirectory = appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public String[] getPersonalPhotosIds() {
        File[] files = mainPhotoDirectory.listFiles();
        String[] ids = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            ids[i] = getIdFromFile(files[i]);
        }
        return ids;
    }

    private String getIdFromFile(File file) {
        return file.getName().split("_")[1];
    }
}
