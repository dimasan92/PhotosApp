package ru.geekbrains.data.util;

import android.content.Context;

import androidx.annotation.StyleRes;

public interface PrefUtils {

    void saveCurrentTheme(Context context, @StyleRes int themeId);

    int currentTheme(Context context, @StyleRes int defaultTheme);
}
