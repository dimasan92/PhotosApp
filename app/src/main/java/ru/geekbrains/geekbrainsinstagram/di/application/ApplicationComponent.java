package ru.geekbrains.geekbrainsinstagram.di.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import ru.geekbrains.geekbrainsinstagram.di.activity.main.MainActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.settings.SettingsActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.module.DataModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.MapperModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.NavigationModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.SchedulersModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.UtilsModule;

@Singleton
@Component(modules = {DataModule.class,
        MapperModule.class,
        UtilsModule.class,
        SchedulersModule.class,
        NavigationModule.class})
public interface ApplicationComponent {

    MainActivityComponent getMainActivityComponent();

    SettingsActivityComponent getSettingsActivityComponent();

    @Component.Builder
    interface Builder {

        ApplicationComponent build();

        @BindsInstance
        Builder setContext(Context context);
    }
}
