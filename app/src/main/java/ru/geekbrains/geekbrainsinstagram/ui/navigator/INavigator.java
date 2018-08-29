package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import androidx.fragment.app.FragmentManager;

public interface INavigator {

    void init(FragmentManager fragmentManager);

    void navigateToPersonalPhotos();

    void navigateToAppTheme();
}
