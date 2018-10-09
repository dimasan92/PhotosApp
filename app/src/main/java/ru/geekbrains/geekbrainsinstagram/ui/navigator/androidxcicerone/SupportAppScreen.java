package ru.geekbrains.geekbrainsinstagram.ui.navigator.androidxcicerone;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import ru.terrakok.cicerone.Screen;

public abstract class SupportAppScreen extends Screen {

    public Fragment getFragment() {
        return null;
    }

    public Intent getActivityIntent(Context context) {
        return null;
    }
}
