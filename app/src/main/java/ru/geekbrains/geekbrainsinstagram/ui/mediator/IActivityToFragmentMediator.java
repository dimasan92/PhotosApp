package ru.geekbrains.geekbrainsinstagram.ui.mediator;

import androidx.appcompat.widget.Toolbar;

public interface IActivityToFragmentMediator {

    interface EventHandler {

        void setToolbar(Toolbar toolbar);
    }

    void init(EventHandler handler);

    void setupToolbar(Toolbar toolbar);
}
