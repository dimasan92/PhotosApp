package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.settings.ShouldChangeThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelPhotosMapper;

@FragmentScope
public final class AppThemePresenter extends BasePresenter<IAppThemePresenter.IView>
        implements IAppThemePresenter {

    private final ShouldChangeThemeUseCase shouldChangeThemeUseCase;

    @Inject
    AppThemePresenter(ShouldChangeThemeUseCase shouldChangeThemeUseCase, IPresentModelPhotosMapper mapper) {
        this.shouldChangeThemeUseCase = shouldChangeThemeUseCase;
    }

    @Override
    public void redThemeSelected() {
        shouldThemeChange(AppThemeModel.RED_THEME);
    }

    @Override
    public void blueThemeSelected() {
        shouldThemeChange(AppThemeModel.BLUE_THEME);
    }

    @Override
    public void greenThemeSelected() {
        shouldThemeChange(AppThemeModel.GREEN_THEME);
    }

    private void shouldThemeChange(AppThemeModel theme) {
        addDisposable(shouldChangeThemeUseCase.execute(theme)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shouldChange -> {
                    if (shouldChange) {
                        view.applyTheme();
                    }
                }, getDefaultErrorHandler()));
    }
}
