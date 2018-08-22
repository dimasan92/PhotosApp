package ru.geekbrains.geekbrainsinstagram.ui.settings.theme;

import android.content.Context;

import ru.geekbrains.geekbrainsinstagram.base.BaseContract;

public interface ColorThemeContract {

    interface View extends BaseContract.View {

        void recreateActivity();

        Context getAppContext();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void chooseRedTheme();

        void chooseBlueTheme();

        void chooseGreenTheme();
    }
}
