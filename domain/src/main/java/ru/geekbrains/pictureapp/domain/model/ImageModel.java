package ru.geekbrains.pictureapp.domain.model;

import java.io.File;

import static ru.geekbrains.pictureapp.domain.util.Constants.EMPTY_STRING;

public final class ImageModel {

    private final String id;
    private final boolean isFavorite;
    private final String filePath;
    private final String regularUrl;
    private final String smallUrl;
    private final String extension;

    public ImageModel(final ImageModel imageModel, final String filePath) {
        this(imageModel.id, imageModel.isFavorite, filePath, imageModel.regularUrl,
                imageModel.smallUrl, imageModel.extension);
    }

    public ImageModel(final ImageModel imageModel, final boolean isFavorite) {
        this(imageModel.id, isFavorite, imageModel.filePath, imageModel.regularUrl,
                imageModel.smallUrl, imageModel.extension);
    }

    public ImageModel(final ImageModel imageModel, final boolean isFavorite, final String filePath) {
        this(imageModel.id, isFavorite, filePath, imageModel.regularUrl,
                imageModel.smallUrl, imageModel.extension);
    }

    public ImageModel(final String id, final boolean isFavorite, final String filePath) {
        this(id, isFavorite, filePath, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
    }

    public ImageModel(final String id, final String filePath, final String extension) {
        this(id, false, filePath, EMPTY_STRING, EMPTY_STRING, extension);
    }

    public ImageModel(final String id, final boolean isFavorite, final String filePath, final String extension) {
        this(id, isFavorite, filePath, EMPTY_STRING, EMPTY_STRING, extension);
    }

    public ImageModel(final String id, final boolean isFavorite, final String filePath,
                      final String regularUrl, final String smallUrl, final String extension) {
        this.id = id;
        this.isFavorite = isFavorite;
        this.filePath = filePath;
        this.regularUrl = regularUrl;
        this.smallUrl = smallUrl;
        this.extension = extension;
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

    public String getRegularUrl() {
        return regularUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public String getExtension() {
        return extension;
    }

    public boolean isPhoto() {
        if (filePath.isEmpty()) {
            return false;
        }
        final String[] array = filePath.split(File.separator);
        return array[array.length - 2].equals("camera_photos");
    }
}
