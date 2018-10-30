package ru.geekbrains.data.util;

public interface PrefUtils {

    void saveCurrentTheme(final String theme);

    String getCurrentTheme(final String defaultTheme);
}
