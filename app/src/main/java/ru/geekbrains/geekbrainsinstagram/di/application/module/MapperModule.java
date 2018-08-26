package ru.geekbrains.geekbrainsinstagram.di.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.mapper.IEntityMapper;
import ru.geekbrains.data.mapper.EntityMapper;
import ru.geekbrains.geekbrainsinstagram.ui.mapper.IModelMapper;
import ru.geekbrains.geekbrainsinstagram.ui.mapper.ModelMapper;

@Module
public final class MapperModule {

    @Singleton
    @Provides
    IEntityMapper provideEntityMapper() {
        return new EntityMapper();
    }

    @Singleton
    @Provides
    IModelMapper provideModelMapper() {
        return new ModelMapper();
    }
}
