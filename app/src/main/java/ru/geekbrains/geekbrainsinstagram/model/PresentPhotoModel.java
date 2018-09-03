package ru.geekbrains.geekbrainsinstagram.model;

import java.util.Objects;
import java.util.UUID;

public final class PresentPhotoModel {

    private final String id;
    private final boolean isFavorite;

    public PresentPhotoModel() {
        id = UUID.randomUUID().toString();
        this.isFavorite = false;
    }

    public PresentPhotoModel(final String id, boolean isFavorite) {
        this.id = id;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public String getPhotoFileName() {
        return "PHOTO_" + id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PresentPhotoModel photoModel = (PresentPhotoModel) o;
        return isFavorite == photoModel.isFavorite &&
                id.equals(photoModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFavorite);
    }
}
