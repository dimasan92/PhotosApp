package ru.geekbrains.geekbrainsinstagram.model;

public enum AppTheme {

    RED_THEME("red_application_theme"),
    BLUE_THEME("blue_application_theme"),
    GREEN_THEME("green_application_theme");

    private final String themeName;

    AppTheme(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeName() {
        return themeName;
    }
}
