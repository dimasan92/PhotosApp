package ru.geekbrains.data.util;

public interface FilesUtils {

    String[] getPhotosIdsFromDevice();

    boolean deletePhotoFromDevice(final String photoId);
}
