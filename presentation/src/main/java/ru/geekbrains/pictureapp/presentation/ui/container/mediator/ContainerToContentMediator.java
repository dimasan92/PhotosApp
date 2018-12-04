package ru.geekbrains.pictureapp.presentation.ui.container.mediator;

import androidx.appcompat.widget.Toolbar;
import ru.geekbrains.pictureapp.presentation.ui.navigator.Screens;

public interface ContainerToContentMediator {

    interface EventHandler {

        void setToolbar(final Toolbar toolbar, final Screens screen);

        void recreate();
    }

    void init(final EventHandler handler);

    void setupToolbar(final Toolbar toolbar, final Screens screen);

    void applyTheme();
}
