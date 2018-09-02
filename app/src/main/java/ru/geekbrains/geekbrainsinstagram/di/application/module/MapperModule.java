package ru.geekbrains.geekbrainsinstagram.di.application.module;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.mapper.EntityMapper;
import ru.geekbrains.data.mapper.IEntityMapper;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelMapper;
import ru.geekbrains.geekbrainsinstagram.model.mapper.PresentModelMapper;

@Module
public interface MapperModule {

    @Singleton
    @Binds
    IEntityMapper provideEntityMapper(EntityMapper entityMapper);

    @Singleton
    @Binds
    IPresentModelMapper provideModelMapper(PresentModelMapper modelMapper);
}
