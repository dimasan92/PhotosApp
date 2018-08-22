package ru.geekbrains.geekbrainsinstagram.ui.settings.theme;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;

public final class ColorThemePresenter extends BasePresenter<ColorThemeContract.View>
        implements ColorThemeContract.Presenter {

    private final Subject<Integer> themeSubject = PublishSubject.create();

    @Override
    public void viewIsReady() {
        observeThemeChanging();
    }

    @Override
    public void chooseRedTheme() {
        themeSubject.onNext(R.style.RedAppTheme);
    }

    @Override
    public void chooseBlueTheme() {
        themeSubject.onNext(R.style.BlueAppTheme);
    }

    @Override
    public void chooseGreenTheme() {
        themeSubject.onNext(R.style.GreenAppTheme);
    }

    private void observeThemeChanging() {
        disposables.add(themeSubject.subscribe(view.getThemeObserver()));
    }
}
