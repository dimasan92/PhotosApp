package ru.geekbrains.geekbrainsinstagram.ui.mediator;

import androidx.appcompat.widget.Toolbar;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

public interface IActivityToFragmentMediator {

    interface EventHandler {

        void setToolbar(Toolbar toolbar);

        void recreate();

        void openFullSizePhoto(PresentPhotoModel photo);
    }

    void init(EventHandler handler);

    void setupToolbar(Toolbar toolbar);

    void themeChanged();

    void openFullSizePhoto(PresentPhotoModel photo);
}
