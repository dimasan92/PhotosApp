package ru.geekbrains.geekbrainsinstagram.ui.containers.main.mediator;

import androidx.appcompat.widget.Toolbar;

public interface MainContainerToContentMediator {

    interface EventHandler {

        void setToolbar(Toolbar toolbar);
    }

    void init(EventHandler handler);

    void setupToolbar(Toolbar toolbar);
}
