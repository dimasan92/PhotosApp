package ru.geekbrains.geekbrainsinstagram.ui.settings.theme;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.utils.SettingsPrefsUtils;

public final class ColorThemePresenter extends BasePresenter<ColorThemeContract.View>
        implements ColorThemeContract.Presenter {

    private final Subject<Integer> themeSubject = PublishSubject.create();

    @Override
    public void viewIsReady() {
    }

    @Override
    public void chooseRedTheme() {
        applyTheme(R.style.RedAppTheme);
    }

    @Override
    public void chooseBlueTheme() {
        applyTheme(R.style.BlueAppTheme);
    }

    @Override
    public void chooseGreenTheme() {
        applyTheme(R.style.GreenAppTheme);
    }

    private void applyTheme(int theme){
        if(view.getAppContext() == null){
            return;
        }
        if (theme != SettingsPrefsUtils.getCurrentTheme(view.getAppContext())) {
            SettingsPrefsUtils.saveCurrentTheme(view.getAppContext(), theme);
            view.recreateActivity();
        }
    }
}
