package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private final Context appContext;

    public ApplicationModule(Context appContext) {
        this.appContext = appContext;
    }

    @Singleton
    @Provides
    Context provideAppContext() {
        return appContext;
    }
}
