package ru.geekbrains.geekbrainsinstagram.ui.settings.theme;

import io.reactivex.functions.Consumer;
import ru.geekbrains.geekbrainsinstagram.base.BaseContract;

public interface ColorThemeContract {

    interface View extends BaseContract.View {

        Consumer<Integer> getThemeObserver();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void chooseRedTheme();

        void chooseBlueTheme();

        void chooseGreenTheme();
    }
}
