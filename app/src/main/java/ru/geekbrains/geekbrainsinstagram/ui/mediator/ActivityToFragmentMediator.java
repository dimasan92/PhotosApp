package ru.geekbrains.geekbrainsinstagram.ui.mediator;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;

@ActivityScope
public final class ActivityToFragmentMediator implements IActivityToFragmentMediator {

    private EventHandler handler;

    @Inject
    ActivityToFragmentMediator() {
    }

    @Override
    public void init(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setupToolbar(Toolbar toolbar) {
        handler.setToolbar(toolbar);
    }

    @Override
    public void themeChanged() {
        handler.recreate();
    }

    @Override
    public void openFullSizePhoto(PresentPhotoModel photo) {
        handler.openFullSizePhoto(photo);
    }
}
