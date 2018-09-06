package ru.geekbrains.geekbrainsinstagram.util;

import android.view.View;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;

@FragmentScope
public final class FragmentUtils implements IFragmentUtils {

    private EventHandler handler;

    @Inject
    FragmentUtils() {
    }

    @Override
    public void init(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setFabListener(View.OnClickListener listener) {
        handler.setFabListener(listener);
    }

    @Override
    public void showNotifyingMessage(int messageId, int duration) {
        handler.makeNotifyingMessage(messageId, duration);
    }
}
