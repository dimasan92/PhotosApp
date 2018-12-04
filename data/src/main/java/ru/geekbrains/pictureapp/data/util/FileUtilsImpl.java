package ru.geekbrains.pictureapp.data.util;

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

import ru.geekbrains.pictureapp.domain.model.ImageModel;

@Singleton
public final class FileUtilsImpl implements FileUtils {

    private static final String PHOTO_FILENAME_PREFIX = "photo_";

    private final File searchPhotosDir;
    private final File cameraPhotosDir;

    @Inject
    FileUtilsImpl(@Named("search_photos_dir") final File searchPhotosDir,
                  @Named("camera_photos_dir") final File cameraPhotosDir) {
        this.searchPhotosDir = searchPhotosDir;
        this.cameraPhotosDir = cameraPhotosDir;
    }

    @Override
    public List<String> getPicturesNamesFromStorage() {
        return getPhotosNamesFromStorage(searchPhotosDir);
    }

    @Override
    public List<String> getPhotosNamesFromStorage() {
        return getPhotosNamesFromStorage(cameraPhotosDir);
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

    @Override
    public String getIdFromFilename(final String filename) {
        return filename.split("_")[1].split("\\.")[0];
    }

    @Override
    public String getFilenameForId(final String id, final String ext) {
        return PHOTO_FILENAME_PREFIX + id + "." + ext;
    }

    @Override
    public String getPictureFilePath(final String filename) {
        return searchPhotosDir.getAbsolutePath() + File.separator + filename;
    }

    @Override
    public String getPhotoFilePath(final String filename) {
        return cameraPhotosDir.getAbsolutePath() + File.separator + filename;
    }

    @Override
    public ImageModel writePictureToDevice(final ImageModel imageModel,
                                           final byte[] photoArray) throws IOException {
        final String filename = getFilenameForId(imageModel.getId(), imageModel.getExtension());
        final String filePath = getPictureFilePath(filename);
        final File photoFile = new File(filePath);
        try (final BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(photoFile))) {
            outputStream.write(photoArray);
        }
        return new ImageModel(imageModel, filePath);
    }

    @Override
    public boolean deletePhotoFromDevice(final ImageModel imageModel) {
        return new File(imageModel.getFilePath()).delete();
    }

    @Override
    public String getPictureExt(final String url) {
        return Uri.parse(url).getQueryParameter("fm");
    }

    @Override
    public String getPhotoExt() {
        return "jpg";
    }

    @Override
    public String getPhotoExt(final String filename) {
        return filename.split("\\.")[1];
    }
}
