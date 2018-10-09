package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;

public interface AppThemePresenter extends BasePresenter<AppThemePresenter.View> {

    interface View extends BasePresenter.View {

        void applyTheme();
    }

    void redThemeSelected();

    void blueThemeSelected();

    void greenThemeSelected();
}
