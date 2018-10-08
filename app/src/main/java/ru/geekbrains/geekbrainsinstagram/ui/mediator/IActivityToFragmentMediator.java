package ru.geekbrains.geekbrainsinstagram.ui.mediator;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

public interface IActivityToFragmentMediator {

    interface EventHandler {

        void setToolbar(Toolbar toolbar);

        void recreate();

        void openFullSizePhoto(List<ViewPhotoModel> photoIds);
    }

    void init(EventHandler handler);

    void setupToolbar(Toolbar toolbar);

    void themeChanged();

    void openFullSizePhoto(List<ViewPhotoModel> photoIds);
}
