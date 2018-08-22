package ru.geekbrains.geekbrainsinstagram.ui.settings.theme;

import ru.geekbrains.geekbrainsinstagram.base.BaseContract;

public interface ColorThemeContract {

    interface View extends BaseContract.View {

        void recreateActivity();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void chooseRedTheme();

        void chooseBlueTheme();

        void chooseGreenTheme();
    }
}
