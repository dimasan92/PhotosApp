package ru.geekbrains.geekbrainsinstagram.ui.containers;

import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;

public interface BaseContainerPresenter<V extends BaseContainerPresenter.View> extends BasePresenter<V> {

    interface View extends BaseView {

        void setTheme(AppThemeModel theme);
    }

    void beforeOnCreate();

    void setScreens(Screens screens);
}
