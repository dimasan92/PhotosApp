package ru.geekbrains.geekbrainsinstagram.di.activity.module;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Navigator;
import ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer.IMainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer.MainPresenter;

@Module
public final class ActivityModule {

    private final FragmentManager fragmentManager;

    public ActivityModule(FragmentActivity activity) {
        fragmentManager = activity.getSupportFragmentManager();
    }

    @ActivityScope
    @Provides
    FragmentManager provideFragmentManager() {
        return fragmentManager;
    }

    @ActivityScope
    @Provides
    INavigator provideNavigator(FragmentManager fragmentManager){
        return new Navigator(fragmentManager);
    }

    @ActivityScope
    @Provides
    IMainPresenter provideMainPresenter() {
        MainPresenter mainPresenter = new MainPresenter();
        MainApplication.getApp().getComponentsManager().getActivityComponent().inject(mainPresenter);
        return mainPresenter;
    }
}
