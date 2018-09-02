package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.settings.ShouldChangeThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelMapper;

@FragmentScope
public final class AppThemePresenter extends BasePresenter<IAppThemePresenter.IView>
        implements IAppThemePresenter {

    private final ShouldChangeThemeUseCase shouldChangeThemeUseCase;
    private final IPresentModelMapper mapper;

    @Inject
    AppThemePresenter(ShouldChangeThemeUseCase shouldChangeThemeUseCase, IPresentModelMapper mapper) {
        this.shouldChangeThemeUseCase = shouldChangeThemeUseCase;
        this.mapper = mapper;
    }

    @Override
    public void viewIsReady() {
    }

    @Override
    public void redThemeSelected() {
        shouldThemeChange(AppTheme.RED_THEME);
    }

    @Override
    public void blueThemeSelected() {
        shouldThemeChange(AppTheme.BLUE_THEME);
    }

    @Override
    public void greenThemeSelected() {
        shouldThemeChange(AppTheme.GREEN_THEME);
    }

    private void shouldThemeChange(AppTheme theme) {
        disposables.add(shouldChangeThemeUseCase.execute(mapper.viewToDomain(theme))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shouldChange -> {
                    if (shouldChange) {
                        view.applyTheme();
                    }
                }));
    }
}
