package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.Fragment;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsActivity;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public final class Screens {

    public enum Screen {
        APP_THEME_SCREEN
    }

    public static final class SettingsContainer extends SupportAppScreen {

        public static final String SCREEN_TO_OPEN = "screen_to_open";
        public static final String PREVIOUS_START_INTENT = "previous_start_intent";

        private final Screen screenToOpen;
        private final Intent previousStartIntent;

        public SettingsContainer(Screen screenToOpen, Intent previousStartIntent) {
            this.screenToOpen = screenToOpen;
            this.previousStartIntent = previousStartIntent;
        }

        public SettingsContainer(Screen screenToOpen) {
            this.screenToOpen = screenToOpen;
            previousStartIntent = null;
        }

        @Override
        public Intent getActivityIntent(Context context) {
            Intent intent = new Intent(context, SettingsActivity.class);
            intent.putExtra(SCREEN_TO_OPEN, screenToOpen);
            if (previousStartIntent != null) {
                intent.putExtra(PREVIOUS_START_INTENT, PREVIOUS_START_INTENT);
            }
            return intent;
        }
    }

    public static final class AppThemeScreen extends SupportAppScreen {

        public Fragment getNewFragment() {
            return AppThemeFragment.newInstance();
        }
    }

    @Singleton
    public static class Mapper {

        private static final String APP_THEME_SCREEN = "app_theme_screen";

        @Inject
        Mapper() {
        }

        public Screen getScreen(String screen) {
            switch (screen) {
                case APP_THEME_SCREEN:
                    return Screen.APP_THEME_SCREEN;
                default:
                    throw new IllegalArgumentException("Wrong string");
            }
        }

        public String mapScreen(Screen screen) {
            switch (screen) {
                case APP_THEME_SCREEN:
                    return APP_THEME_SCREEN;
                default:
                    throw new IllegalArgumentException("Wrong string");
            }
        }
    }
}
