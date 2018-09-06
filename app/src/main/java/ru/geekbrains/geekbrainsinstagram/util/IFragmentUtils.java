package ru.geekbrains.geekbrainsinstagram.util;

import android.view.View;

public interface IFragmentUtils {

    interface EventHandler {

        void setFabListener(View.OnClickListener listener);
    }

    void init(EventHandler handler);

    void setFabListener(View.OnClickListener listener);
}
