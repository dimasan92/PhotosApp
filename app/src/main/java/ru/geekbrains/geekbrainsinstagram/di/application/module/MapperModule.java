package ru.geekbrains.geekbrainsinstagram.di.application.module;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.data.mapper.EntityPhotoMapperImpl;
import ru.geekbrains.data.mapper.EntityPhotoMapper;
import ru.geekbrains.data.mapper.ThemeMapper;
import ru.geekbrains.data.mapper.ThemeMapperImpl;
import ru.geekbrains.geekbrainsinstagram.model.mapper.ViewPhotoModelMapper;
import ru.geekbrains.geekbrainsinstagram.model.mapper.ViewPhotoModelMapperImpl;

@Module
public interface MapperModule {

    @Singleton
    @Binds
    EntityPhotoMapper provideEntityPhotoMapper(EntityPhotoMapperImpl entityPhotoMapper);

    @Singleton
    @Binds
    ThemeMapper provideThemeMapper(ThemeMapperImpl themeMapperImpl);

    @Singleton
    @Binds
    ViewPhotoModelMapper provideModelMapper(ViewPhotoModelMapperImpl modelMapper);
}
