package ru.geekbrains.geekbrainsinstagram.model;

import java.util.Objects;
import java.util.UUID;

public final class PhotoModel {

    private final String id;
    private String uri;
    private boolean isFavorite;

    public PhotoModel() {
        id = UUID.randomUUID().toString();
    }

    public PhotoModel(final String id, String uri, boolean isFavorite) {
        this.id = id;
        this.uri = uri;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public String getPhotoFileName() {
        return toString() + ".jpeg";
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhotoModel photoModel = (PhotoModel) o;
        return isFavorite == photoModel.isFavorite &&
                id.equals(photoModel.id) &&
                uri.equals(photoModel.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uri, isFavorite);
    }

    @Override
    public String toString() {
        return "PHOTO_" + id;
    }
}
