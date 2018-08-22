package ru.geekbrains.geekbrainsinstagram.di.application;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityComponent;
import ru.geekbrains.geekbrainsinstagram.di.activity.module.ActivityModule;
import ru.geekbrains.geekbrainsinstagram.di.application.module.ApplicationModule;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    ActivityComponent getActivityComponent(ActivityModule activityModule);
}
