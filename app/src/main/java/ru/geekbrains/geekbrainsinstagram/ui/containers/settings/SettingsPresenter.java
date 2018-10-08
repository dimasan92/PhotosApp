package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;

public interface SettingsPresenter extends IBasePresenter<SettingsPresenter.View> {

    interface View extends IBasePresenter.IView {

        void setTheme(AppThemeModel theme);
    }

    void setNavigator(INavigator navigator);

    void beforeOnCreate();

    void afterOnCreate();
}
