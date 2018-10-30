package ru.geekbrains.data.util;

import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ru.geekbrains.domain.model.PhotoModel;

@Singleton
public final class FileUtilsImpl implements FileUtils {

    private static final String PHOTO_FILENAME_PREFIX = "photo_";

    private final File searchPhotosDir;
    private final File cameraPhotosDir;

    @Inject FileUtilsImpl(@Named("search_photos_dir") final File searchPhotosDir,
                          @Named("camera_photos_dir") final File cameraPhotosDir) {
        this.searchPhotosDir = searchPhotosDir;
        this.cameraPhotosDir = cameraPhotosDir;
    }

    @Override public List<String> getSearchPhotosNamesFromStorage() {
        return getPhotosNamesFromStorage(searchPhotosDir);
    }

    @Override public List<String> getCameraPhotosNamesFromStorage() {
        return getPhotosNamesFromStorage(cameraPhotosDir);
    }

    @Override public String getIdFromFilename(final String filename) {
        return filename.split("_")[1].split("\\.")[0];
    }

    @Override public String getFilenameForId(final String id, final String ext) {
        return PHOTO_FILENAME_PREFIX + id + "." + ext;
    }

    @Override public String getSearchPhotoFilePath(final String filename) {
        return searchPhotosDir.getAbsolutePath() + File.separator + filename;
    }

    @Override public String getCameraPhotoFilePath(final String filename) {
        return cameraPhotosDir.getAbsolutePath() + File.separator + filename;
    }

    @Override public PhotoModel writeSearchPhotoToDevice(final PhotoModel photoModel,
                                                         final byte[] photoArray) throws IOException {
        final String filename = getFilenameForId(photoModel.getId(), photoModel.getPhotoExt());
        final String filePath = getSearchPhotoFilePath(filename);
        final File photoFile = new File(filePath);
        try (final BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(photoFile))) {
            outputStream.write(photoArray);
        }
        return new PhotoModel(photoModel, filePath);
    }

    @Override public boolean deletePhotoFromDevice(final PhotoModel photoModel) {
        return new File(photoModel.getFilePath()).delete();
    }

    @Override public String getSearchPhotoExt(final String url) {
        return Uri.parse(url).getQueryParameter("fm");
    }

    @Override public String getCameraPhotoExt() {
        return "jpg";
    }

    @Override public String getPhotoExt(final String filename) {
        return filename.split("\\.")[1];
    }

    private List<String> getPhotosNamesFromStorage(final File dir) {
        final List<String> names = new ArrayList<>();
        for (final File file : dir.listFiles()) {
            if (file.isFile()) {
                names.add(file.getName());
            }
        }
        return names;
    }
}
