package ru.geekbrains.geekbrainsinstagram.di.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import ru.geekbrains.geekbrainsinstagram.di.application.modules.DataModule;
import ru.geekbrains.geekbrainsinstagram.di.application.modules.MapperModule;
import ru.geekbrains.geekbrainsinstagram.di.application.modules.SchedulersModule;
import ru.geekbrains.geekbrainsinstagram.di.application.modules.ServiceModule;
import ru.geekbrains.geekbrainsinstagram.di.application.modules.UtilsModule;
import ru.geekbrains.geekbrainsinstagram.di.ui.MainComponent;

@Singleton
@Component(modules = {DataModule.class,
        MapperModule.class,
        SchedulersModule.class,
        ServiceModule.class,
        UtilsModule.class})
public interface ApplicationComponent {

    MainComponent getMainComponent();

    @Component.Builder
    interface Builder {

        ApplicationComponent build();

        @BindsInstance Builder setContext(final Context context);
    }
}
