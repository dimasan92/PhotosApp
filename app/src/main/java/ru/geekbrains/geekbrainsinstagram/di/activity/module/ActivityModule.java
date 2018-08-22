package ru.geekbrains.geekbrainsinstagram.di.activity.module;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.MainContract;
import ru.geekbrains.geekbrainsinstagram.ui.MainPresenter;

@Module
public class ActivityModule {

    private FragmentManager fragmentManager;

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
    MainContract.Presenter providePresenter() {
        return new MainPresenter();
    }
}
