package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsActivity;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.androidxcicerone.SupportAppScreen;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemeFragment;

@Singleton
public final class Screens {

    public static final String SCREEN_TO_OPEN = "screen_to_open";

    public enum Screen {
        APP_THEME_SCREEN
    }

    private FragmentManager fragmentManager;

    @Inject
    Screens() {
    }

    public void init(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void release() {
        fragmentManager = null;
    }

    public SupportAppScreen getSettingsContainer(Screen screenToOpen) {
        return new SupportAppScreen() {
            @Override
            public Intent getActivityIntent(Context context) {
                Intent intent = new Intent(context, SettingsActivity.class);
                intent.putExtra(SCREEN_TO_OPEN, screenToOpen);
                return intent;
            }
        };
    }

    public SupportAppScreen getAppThemeScreen() {
        return new SupportAppScreen() {
            @Override
            public Fragment getFragment() {
                return AppThemeFragment.newInstance();
            }
        };
    }
}
