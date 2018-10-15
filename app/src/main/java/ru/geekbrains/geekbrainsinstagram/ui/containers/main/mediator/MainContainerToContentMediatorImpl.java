package ru.geekbrains.geekbrainsinstagram.ui.containers.main.mediator;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;

public final class MainContainerToContentMediatorImpl implements MainContainerToContentMediator {

    private EventHandler handler;

    @Inject
    MainContainerToContentMediatorImpl() {
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
