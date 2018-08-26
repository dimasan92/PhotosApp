package ru.geekbrains.domain.model;

public final class Photo {

    private final String id;
    private final String uri;
    private final boolean isFavorite;

    public Photo(String  id, String uri, boolean isFavorite) {
        this.id = id;
        this.uri = uri;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
