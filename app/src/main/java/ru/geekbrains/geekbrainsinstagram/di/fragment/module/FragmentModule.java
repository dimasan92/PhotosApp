package ru.geekbrains.geekbrainsinstagram.di.fragment.module;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.IPersonalPhotosPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.IAppThemePresenter;
import ru.geekbrains.geekbrainsinstagram.util.FragmentUtils;
import ru.geekbrains.geekbrainsinstagram.util.IFragmentUtils;

@Module
public interface FragmentModule {

    @FragmentScope
    @Binds
    IAppThemePresenter provideAppThemePresenter(AppThemePresenter presenter);

    @FragmentScope
    @Binds
    IFragmentUtils provideIFragmentUtils(FragmentUtils fragmentUtils);

    @FragmentScope
    @Binds
    IPersonalPhotosPresenter providePersonalPhotosPresenter(PersonalPhotosPresenter presenter);
}
