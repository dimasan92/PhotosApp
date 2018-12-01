package ru.geekbrains.pictureapp.presentation.di.application.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.data.mapper.ImageMapper;
import ru.geekbrains.pictureapp.data.mapper.ImageMapperImpl;
import ru.geekbrains.pictureapp.data.mapper.ThemeMapper;
import ru.geekbrains.pictureapp.data.mapper.ThemeMapperImpl;

@Module
public interface MapperModule {

    @Singleton
    @Binds
    ImageMapper provideEntityPhotoMapper(final ImageMapperImpl entityPhotoMapper);

    @Singleton
    @Binds
    ThemeMapper provideThemeMapper(final ThemeMapperImpl themeMapperImpl);
}
