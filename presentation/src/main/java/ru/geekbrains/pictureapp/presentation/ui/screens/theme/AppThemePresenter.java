package ru.geekbrains.pictureapp.presentation.ui.screens.theme;

import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;

import static ru.geekbrains.pictureapp.presentation.ui.screens.theme.AppThemePresenter.ThemeView;

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
