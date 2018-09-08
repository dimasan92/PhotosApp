package ru.geekbrains.geekbrainsinstagram.di.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.module.DataModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.MapperModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.UtilsModule;

@Singleton
@Component(modules = {DataModule.class, MapperModule.class, UtilsModule.class})
public interface ApplicationComponent {

    ActivityComponent getActivityComponent();

    @Component.Builder
    interface Builder {

        ApplicationComponent build();

        @BindsInstance
        Builder setContext(Context context);
    }
}
