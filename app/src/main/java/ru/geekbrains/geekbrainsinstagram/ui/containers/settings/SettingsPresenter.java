package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;

public interface SettingsPresenter extends BaseContainerPresenter<SettingsPresenter.View> {

    interface View extends BaseContainerPresenter.View {

        void close();
    }

    void viewIsReady(Screens.Screen screen);

    void back();
}
