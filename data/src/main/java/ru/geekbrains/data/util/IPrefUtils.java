package ru.geekbrains.data.util;

import androidx.annotation.StyleRes;

public interface IPrefUtils {

    void saveCurrentTheme(@StyleRes int themeId);

    int currentTheme();
}
