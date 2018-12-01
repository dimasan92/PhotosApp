package ru.geekbrains.pictureapp.data.util;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.pictureapp.domain.model.ImageModel;

public interface FileUtils {

    List<String> getPicturesNamesFromStorage();

    List<String> getPhotosNamesFromStorage();

    String getIdFromFilename(final String filename);

    String getFilenameForId(final String id, final String ext);

    String getPictureFilePath(final String filename);

    String getPhotoFilePath(final String filename);

    ImageModel writePictureToDevice(final ImageModel imageModel, final byte[] photoArray)
            throws IOException;

    boolean deletePhotoFromDevice(final ImageModel imageModel);

    String getPictureExt(final String url);

    String getPhotoExt();

    String getPhotoExt(final String filename);
}
