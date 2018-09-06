package ru.geekbrains.geekbrainsinstagram.util;

import android.view.View;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;

@ActivityScope
public final class FragmentUtils implements IFragmentUtils {

    private EventHandler handler;

    @Inject
    public FragmentUtils() {
    }

    @Override
    public void init(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setFabListener(View.OnClickListener listener) {
        handler.setFabListener(listener);
    }
}
