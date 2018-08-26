package ru.geekbrains.geekbrainsinstagram.ui.model;

import java.util.UUID;

public final class PhotoModel {

    private final String id;
    private String uri;
    private boolean isFavorite;

    public PhotoModel() {
        id = UUID.randomUUID().toString();
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
    public String toString() {
        return "PHOTO_" + id;
    }
}
