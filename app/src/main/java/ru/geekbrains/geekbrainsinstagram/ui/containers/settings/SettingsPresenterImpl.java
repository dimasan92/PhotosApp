package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.di.activity.settings.SettingsActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerPresenterImpl;

@SettingsActivityScope
public final class SettingsPresenterImpl extends BaseContainerPresenterImpl<SettingsPresenter.View>
        implements SettingsPresenter {

    @Inject
    SettingsPresenterImpl(GetCurrentThemeUseCase getCurrentThemeUseCase) {
        super(getCurrentThemeUseCase);
    }
}
