package ru.geekbrains.geekbrainsinstagram.util;

import androidx.appcompat.widget.Toolbar;

public interface IActivityUtils {

    interface EventHandler {

        void setToolbar(Toolbar toolbar);
    }

    void init(EventHandler handler);

    void setupToolbar(Toolbar toolbar);
}
