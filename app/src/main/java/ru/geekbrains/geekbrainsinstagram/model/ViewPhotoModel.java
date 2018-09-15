package ru.geekbrains.geekbrainsinstagram.model;

import java.util.Objects;
import java.util.UUID;

public final class ViewPhotoModel {

    private final String id;
    private final boolean isFavorite;

    public ViewPhotoModel() {
        id = UUID.randomUUID().toString();
        this.isFavorite = false;
    }

    public ViewPhotoModel(final String id) {
        this.id = id;
        isFavorite = false;
    }

    public ViewPhotoModel(final String id, final boolean isFavorite) {
        this.id = id;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public String getPhotoFileName() {
        return id;
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
        ViewPhotoModel photoModel = (ViewPhotoModel) o;
        return isFavorite == photoModel.isFavorite &&
                id.equals(photoModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFavorite);
    }
}
