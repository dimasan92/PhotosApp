package ru.geekbrains.geekbrainsinstagram.di.fragment.module;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemeContract;
import ru.geekbrains.geekbrainsinstagram.ui.settings.theme.ColorThemePresenter;

@Module
public final class FragmentModule {

    @Provides
    @FragmentScope
    ColorThemeContract.Presenter provideColorThemePresenter(){
        return new ColorThemePresenter();
    }
}
