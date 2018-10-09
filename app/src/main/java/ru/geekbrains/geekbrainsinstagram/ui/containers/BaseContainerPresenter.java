package ru.geekbrains.geekbrainsinstagram.ui.containers;

import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;

public interface BaseContainerPresenter<V extends BaseContainerPresenter.View> extends BasePresenter<V> {

    interface View extends BasePresenter.View {

        void setTheme(AppThemeModel theme);
    }

    void beforeOnCreate();
}
