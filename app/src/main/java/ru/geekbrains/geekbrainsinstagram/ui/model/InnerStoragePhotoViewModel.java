package ru.geekbrains.geekbrainsinstagram.ui.model;

import android.net.Uri;

import java.util.UUID;

import androidx.annotation.NonNull;

public final class InnerStoragePhotoViewModel {

    private final UUID id;
    private Uri uri;
    private boolean isFavorite;

    public InnerStoragePhotoViewModel() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
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
        return "INNER_STORAGE_PHOTO_" + id.toString();
    }
}
