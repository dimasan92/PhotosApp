package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenter;

import static ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter.ThemeView;

public interface AppThemePresenter extends BasePresenter<ThemeView> {

    interface ThemeView extends BasePresenter.BaseView {

        void applyTheme();

        void release();
    }

    void redThemeSelected();

    void blueThemeSelected();

    void greenThemeSelected();

    void back();
}
