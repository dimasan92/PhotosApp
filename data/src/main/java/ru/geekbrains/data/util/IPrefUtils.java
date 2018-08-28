package ru.geekbrains.data.util;

import androidx.annotation.StyleRes;

public interface PrefUtils {

    void saveCurrentTheme(@StyleRes int themeId);

    int currentTheme();
}
