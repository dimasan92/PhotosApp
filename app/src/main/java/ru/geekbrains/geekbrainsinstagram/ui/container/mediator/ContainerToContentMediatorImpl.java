package ru.geekbrains.geekbrainsinstagram.ui.container.mediator;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import ru.geekbrains.geekbrainsinstagram.di.ui.MainScope;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;

@MainScope
public final class ContainerToContentMediatorImpl implements ContainerToContentMediator {

    private EventHandler handler;

    @Inject ContainerToContentMediatorImpl() {
    }

    @Override public void init(final EventHandler handler) {
        this.handler = handler;
    }

    @Override public void setupToolbar(final Toolbar toolbar, final Screens screen) {
        handler.setToolbar(toolbar, screen);
    }

    @Override public void applyTheme() {
        handler.recreate();
    }
}
