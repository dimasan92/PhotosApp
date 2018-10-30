package ru.geekbrains.geekbrainsinstagram.ui.container;

import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.MainNavigator;

public interface MainPresenter extends BasePresenter<MainPresenter.MainView> {

    interface MainView extends BasePresenter.BaseView {

        void setTheme(final AppThemeModel theme);

        void exit();
    }

    void setNavigator(final MainNavigator navigator);

    void beforeOnCreate();

    void viewFirstCreated();
}
