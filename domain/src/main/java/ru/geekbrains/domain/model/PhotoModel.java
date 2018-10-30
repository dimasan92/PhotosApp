package ru.geekbrains.domain.model;

import java.io.File;

public final class PhotoModel {

    private final String id;
    private final boolean isFavorite;
    private final String filePath;
    private final String regularPhotoUrl;
    private final String smallPhotoUrl;
    private final String photoExt;

    public PhotoModel(final PhotoModel photoModel, final String filePath) {
        this(photoModel.id, photoModel.isFavorite, filePath, photoModel.regularPhotoUrl,
                photoModel.smallPhotoUrl, photoModel.photoExt);
    }

    public PhotoModel(final PhotoModel photoModel, final boolean isFavorite) {
        this(photoModel.id, isFavorite, photoModel.filePath, photoModel.regularPhotoUrl,
                photoModel.smallPhotoUrl, photoModel.photoExt);
    }

    public PhotoModel(final PhotoModel photoModel, final boolean isFavorite, final String filePath) {
        this(photoModel.id, isFavorite, filePath, photoModel.regularPhotoUrl,
                photoModel.smallPhotoUrl, photoModel.photoExt);
    }

    public PhotoModel(final String id, final String filePath) {
        this(id, true, filePath, "", "", "");
    }

    public PhotoModel(final String id, final String filePath, final String photoExt) {
        this(id, false, filePath, "", "", photoExt);
    }

    public PhotoModel(final String id, final boolean isFavorite, final String filePath, final String photoExt) {
        this(id, isFavorite, filePath, "", "", photoExt);
    }

    public PhotoModel(final String id, final boolean isFavorite, final String filePath,
                      final String regularPhotoUrl, final String smallPhotoUrl, final String photoExt) {
        this.id = id;
        this.isFavorite = isFavorite;
        this.filePath = filePath;
        this.regularPhotoUrl = regularPhotoUrl;
        this.smallPhotoUrl = smallPhotoUrl;
        this.photoExt = photoExt;
    }

    public String getId() {
        return id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isSaved() {
        return !filePath.isEmpty();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getRegularPhotoUrl() {
        return regularPhotoUrl;
    }

    public String getSmallPhotoUrl() {
        return smallPhotoUrl;
    }

    public String getPhotoExt() {
        return photoExt;
    }

    public boolean isSearchPhoto() {
        if (filePath.isEmpty()) {
            return false;
        }
        final String[] array = filePath.split(File.separator);
        return array[array.length - 2].equals("search_photos");
    }

    public boolean isCameraPhoto() {
        if (filePath.isEmpty()) {
            return false;
        }
        final String[] array = filePath.split(File.separator);
        return array[array.length - 2].equals("camera_photos");
    }
}
