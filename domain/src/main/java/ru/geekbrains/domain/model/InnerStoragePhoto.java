package ru.geekbrains.domain.model;

import java.util.UUID;

public final class InnerStoragePhoto {

    private final UUID id;
    private final String uri;
    private final boolean isFavorite;

    public InnerStoragePhoto(UUID id, String uri, boolean isFavorite) {
        this.id = id;
        this.uri = uri;
        this.isFavorite = isFavorite;
    }

    public UUID getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
