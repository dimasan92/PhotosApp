package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import androidx.fragment.app.FragmentManager;
import ru.geekbrains.geekbrainsinstagram.di.fragment.ContentDisposer;

public interface INavigator {

    void init(FragmentManager fragmentManager, ContentDisposer disposer);

    void navigateToPersonalPhotos();

    void navigateToAppTheme();
}
