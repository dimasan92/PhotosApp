package ru.geekbrains.geekbrainsinstagram.di.childfragment.module;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.childfragment.ChildFragmentScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.IPersonalPhotosPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosPresenter;

@Module
public interface ChildFragmentModule {

    @ChildFragmentScope
    @Binds
    IPersonalPhotosPresenter providePersonalPhotosPresenter(PersonalPhotosPresenter presenter);
}
