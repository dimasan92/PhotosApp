package ru.geekbrains.pictureapp.presentation.di.ui.details;

import dagger.Subcomponent;
import ru.geekbrains.pictureapp.presentation.di.ui.details.modules.DetailsModule;
import ru.geekbrains.pictureapp.presentation.ui.screens.details.DetailsFragment;

@DetailsScope
@Subcomponent(modules = DetailsModule.class)
public interface DetailsComponent {

    void inject(final DetailsFragment detailsFragment);
}
