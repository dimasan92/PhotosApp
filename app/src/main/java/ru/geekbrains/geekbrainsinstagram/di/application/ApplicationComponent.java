package ru.geekbrains.geekbrainsinstagram.di.application;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.application.module.ApplicationModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.DataModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.MapperModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.UtilsModule;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, MapperModule.class, UtilsModule.class})
public interface ApplicationComponent {

    ActivityComponent getActivityComponent();
}
