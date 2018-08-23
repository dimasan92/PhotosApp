package ru.geekbrains.geekbrainsinstagram.ui.model;

import java.util.UUID;

import androidx.annotation.NonNull;

public final class InnerStoragePhotoModel {

    private final UUID id;

    public InnerStoragePhotoModel() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getPhotoFileName() {
        return toString() + ".jpeg";
    }

    @NonNull
    @Override
    public String toString() {
        return "INNER_STORAGE_PHOTO_" + id.toString();
    }
}
