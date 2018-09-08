package ru.geekbrains.geekbrainsinstagram.di.application.module;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.data.mapper.EntityMapper;
import ru.geekbrains.data.mapper.IEntityMapper;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelPhotosMapper;
import ru.geekbrains.geekbrainsinstagram.model.mapper.PresentModelPhotosMapper;

@Module
public interface MapperModule {

    @Singleton
    @Binds
    IEntityMapper provideEntityMapper(EntityMapper entityMapper);

    @Singleton
    @Binds
    IPresentModelPhotosMapper provideModelMapper(PresentModelPhotosMapper modelMapper);
}
