package ru.geekbrains.geekbrainsinstagram.di.application.module;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.data.mapper.EntityPhotosMapper;
import ru.geekbrains.data.mapper.IEntityPhotosMapper;
import ru.geekbrains.data.mapper.IThemeMapper;
import ru.geekbrains.data.mapper.ThemeMapper;
import ru.geekbrains.geekbrainsinstagram.model.mapper.ViewPhotoModelMapper;
import ru.geekbrains.geekbrainsinstagram.model.mapper.ViewPhotoModelMapperImpl;

@Module
public interface MapperModule {

    @Singleton
    @Binds
    IEntityPhotosMapper provideEntityMapper(EntityPhotosMapper entityMapper);

    @Singleton
    @Binds
    IThemeMapper provideThemeMapper(ThemeMapper themeMapper);

    @Singleton
    @Binds
    ViewPhotoModelMapper provideModelMapper(ViewPhotoModelMapperImpl modelMapper);
}
