package ru.geekbrains.geekbrainsinstagram.ui.navigator;

public enum Screen {

    HOME_SCREEN,
    FAVORITES_SCREEN,
    PROFILE_SCREEN,
    APP_THEME_SCREEN,
    PHOTO_DETAILS_SCREEN;

    public static class Mapper {

        private static final String HOME_SCREEN = "home_screen";
        private static final String FAVORITES_SCREEN = "favorites_screen";
        private static final String PROFILE_SCREEN = "profile_screen";
        private static final String APP_THEME_SCREEN = "app_theme_screen";
        private static final String PHOTO_DETAILS_SCREEN = "photo_details_screen";

        public Screen getScreen(String screen) {
            switch (screen) {
                case HOME_SCREEN:
                    return Screen.HOME_SCREEN;
                case FAVORITES_SCREEN:
                    return Screen.FAVORITES_SCREEN;
                case PROFILE_SCREEN:
                    return Screen.PROFILE_SCREEN;
                case APP_THEME_SCREEN:
                    return Screen.APP_THEME_SCREEN;
                case PHOTO_DETAILS_SCREEN:
                    return Screen.PHOTO_DETAILS_SCREEN;
                default:
                    throw new IllegalArgumentException("Wrong string");
            }
        }

        public String mapScreen(Screen screen) {
            switch (screen) {
                case HOME_SCREEN:
                    return HOME_SCREEN;
                case FAVORITES_SCREEN:
                    return FAVORITES_SCREEN;
                case PROFILE_SCREEN:
                    return PROFILE_SCREEN;
                case APP_THEME_SCREEN:
                    return APP_THEME_SCREEN;
                case PHOTO_DETAILS_SCREEN:
                    return PHOTO_DETAILS_SCREEN;
                default:
                    throw new IllegalArgumentException("Wrong string");
            }
        }
    }
}
