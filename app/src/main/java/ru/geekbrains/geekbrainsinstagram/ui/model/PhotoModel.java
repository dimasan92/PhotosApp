package ru.geekbrains.geekbrainsinstagram.ui.model;

import android.net.Uri;

import java.util.UUID;

import androidx.annotation.NonNull;

public final class PhotoModel {

    private final String id;
    private Uri uri;
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

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @NonNull
    @Override
    public String toString() {
        return "PHOTO_" + id.toString();
    }
}
