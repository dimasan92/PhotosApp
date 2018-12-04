package ru.geekbrains.pictureapp.presentation.di.ui.details.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.details.DetailsScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.details.DetailsPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.details.DetailsPresenterImpl;

@Module
public interface DetailsModule {

    @DetailsScope
    @Binds
    DetailsPresenter provideAppThemePresenter(final DetailsPresenterImpl presenter);
}
