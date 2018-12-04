package ru.geekbrains.pictureapp.presentation.ui.container;

import ru.geekbrains.pictureapp.domain.model.AppThemeModel;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;

public interface MainPresenter extends BasePresenter<MainPresenter.MainView> {

    interface MainView extends BasePresenter.BaseView {

        void setTheme(final AppThemeModel theme);

        void exit();
    }

    void setNavigator(final MainNavigator navigator);

    void beforeOnCreate();

    void viewFirstCreated();
}
