package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.settings.ChangeThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;

public final class AppThemePresenter extends BasePresenter<IAppThemePresenter.IView>
        implements IAppThemePresenter {

    @Inject
    ChangeThemeUseCase changeThemeUseCase;

    @Override
    public void viewIsReady() {
    }

    @Override
    public void redThemeSelected() {
        shouldThemeChange(R.style.RedAppTheme);
    }

    @Override
    public void blueThemeSelected() {
        shouldThemeChange(R.style.BlueAppTheme);
    }

    @Override
    public void greenThemeSelected() {
        shouldThemeChange(R.style.GreenAppTheme);
    }

    private void shouldThemeChange(int theme) {
        disposables.add(changeThemeUseCase.execute(theme)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(shouldChange -> {
                            if (shouldChange) {
                                view.applyTheme();
                            }
                        }));
    }
}
