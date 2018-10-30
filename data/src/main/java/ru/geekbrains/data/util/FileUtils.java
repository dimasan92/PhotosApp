package ru.geekbrains.data.util;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.domain.model.PhotoModel;

public interface FileUtils {

    List<String> getSearchPhotosNamesFromStorage();

    List<String> getCameraPhotosNamesFromStorage();

    String getIdFromFilename(final String filename);

    String getFilenameForId(final String id, final String ext);

    String getSearchPhotoFilePath(final String filename);

    String getCameraPhotoFilePath(final String filename);

    PhotoModel writeSearchPhotoToDevice(final PhotoModel photoModel, final byte[] photoArray)
            throws IOException;

    boolean deletePhotoFromDevice(final PhotoModel photoModel);

    String getSearchPhotoExt(final String url);

    String getCameraPhotoExt();

    String getPhotoExt(final String filename);
}
