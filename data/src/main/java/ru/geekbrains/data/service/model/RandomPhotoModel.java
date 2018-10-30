package ru.geekbrains.data.service.model;

public final class RandomPhotoModel {

    private final String id;
    private final Urls urls;

    public RandomPhotoModel(final Urls urls, final String id) {
        this.id = id;
        this.urls = urls;
    }

    public String getId() {
        return id;
    }

    public Urls getUrls() {
        return urls;
    }
}
