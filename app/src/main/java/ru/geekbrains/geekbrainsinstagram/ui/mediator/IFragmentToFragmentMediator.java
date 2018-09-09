package ru.geekbrains.geekbrainsinstagram.ui.mediator;

import android.view.View;

import androidx.annotation.StringRes;

public interface IFragmentToFragmentMediator {

    interface EventHandler {

        void setFabListener(View.OnClickListener listener);

        void makeNotifyingMessage(@StringRes int messageId, int duration);
    }

    void init(EventHandler handler);

    void setFabListener(View.OnClickListener listener);

    void showNotifyingMessage(@StringRes int messageId, int duration);
}
