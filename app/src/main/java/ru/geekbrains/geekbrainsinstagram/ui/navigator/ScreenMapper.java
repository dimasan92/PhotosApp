package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ScreenMapper {

    private static final String APP_THEME_SCREEN = "app_theme_screen";

    @Inject
    ScreenMapper() {
    }

    public Screens.Screen getScreen(String screen) {
        switch (screen) {
            case APP_THEME_SCREEN:
                return Screens.Screen.APP_THEME_SCREEN;
            default:
                throw new IllegalArgumentException("Wrong string");
        }
    }

    public String mapScreen(Screens.Screen screen) {
        switch (screen) {
            case APP_THEME_SCREEN:
                return APP_THEME_SCREEN;
            default:
                throw new IllegalArgumentException("Wrong string");
        }
    }
}
