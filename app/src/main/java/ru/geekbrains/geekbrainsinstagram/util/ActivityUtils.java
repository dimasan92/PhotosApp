package ru.geekbrains.geekbrainsinstagram.util;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;

@ActivityScope
public final class ActivityUtils implements IActivityUtils {

    private EventHandler handler;

    @Inject
    public ActivityUtils() {
    }

    @Override
    public void init(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setupToolbar(Toolbar toolbar) {
        handler.setToolbar(toolbar);
    }
}
