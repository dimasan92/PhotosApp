package ru.geekbrains.pictureapp.presentation.di.application.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public final class SchedulersModule {

    @Singleton
    @Provides
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
