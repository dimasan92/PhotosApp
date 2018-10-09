package ru.geekbrains.geekbrainsinstagram.ui.containers.settings.mediator;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.di.activity.settings.SettingsActivityScope;

@SettingsActivityScope
public final class SettingsContainerToContentMediatorImpl implements SettingsContainerToContentMediator {

    private EventHandler handler;

    @Inject
    SettingsContainerToContentMediatorImpl() {
    }

    @Override
    public void init(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void themeChanged() {
        handler.themeChanged();
    }
}
