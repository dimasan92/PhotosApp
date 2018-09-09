package ru.geekbrains.geekbrainsinstagram.di.childfragment;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.childfragment.module.ChildFragmentModule;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosFragment;

@ChildFragmentScope
@Subcomponent(modules = {ChildFragmentModule.class})
public interface ChildFragmentComponent {

    void inject(PersonalPhotosFragment view);
}
