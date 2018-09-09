package ru.geekbrains.data.util;

public interface IFilesUtils {

    String[] getPhotosIdsFromDevice();

    boolean deletePhotoFromDevice(String photoId);
}
