package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.di.activity.settings.SettingsActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;
import ru.terrakok.cicerone.Router;

@SettingsActivityScope
public final class SettingsPresenterImpl extends BaseContainerPresenterImpl<SettingsPresenter.View>
        implements SettingsPresenter {

    @Inject
    SettingsPresenterImpl(GetCurrentThemeUseCase getCurrentThemeUseCase, Router router) {
        super(getCurrentThemeUseCase, router);
    }

    @Override
    public void viewIsReady(Screens.Screen screen) {
        switch (screen) {
            case APP_THEME_SCREEN:
                router.navigateTo(screens.getAppThemeScreen());
                break;
            default:
                throw new IllegalArgumentException("Wrong screen type");
        }
    }

    @Override
    public void back() {
        view.close();
    }
}
