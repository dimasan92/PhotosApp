package ru.geekbrains.pictureapp.data.service.model;

public final class Urls {

    private final String regular;
    private final String small;

    public Urls(final String regular, final String small) {
        this.regular = regular;
        this.small = small;
    }

    public String getRegular() {
        return regular;
    }

    public String getSmall() {
        return small;
    }
}
