package ru.geekbrains.geekbrainsinstagram.ui.containers.settings.mediator;

public interface SettingsContainerToContentMediator {

    interface EventHandler {

        void themeChanged();
    }

    void init(EventHandler handler);

    void themeChanged();
}
