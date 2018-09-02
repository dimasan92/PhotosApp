package ru.geekbrains.domain.model;

public final class PhotoModel {

    private final String id;
    private final boolean isFavorite;

    public PhotoModel(String id, boolean isFavorite) {
        this.id = id;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
