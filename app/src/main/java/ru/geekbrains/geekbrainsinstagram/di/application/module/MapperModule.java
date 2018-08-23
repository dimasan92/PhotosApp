package ru.geekbrains.geekbrainsinstagram.di.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.mapper.DataMapper;
import ru.geekbrains.data.mapper.DataMapperImpl;
import ru.geekbrains.geekbrainsinstagram.ui.mapper.ViewMapper;
import ru.geekbrains.geekbrainsinstagram.ui.mapper.ViewMapperImpl;

@Module
public final class MapperModule {

    @Singleton
    @Provides
    DataMapper provideMapper() {
        return new DataMapperImpl();
    }

    @Singleton
    @Provides
    ViewMapper providesMapper() {
        return new ViewMapperImpl();
    }
}
