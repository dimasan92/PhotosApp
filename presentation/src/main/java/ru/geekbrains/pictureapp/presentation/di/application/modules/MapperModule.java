package ru.geekbrains.pictureapp.presentation.di.application.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.data.mapper.PhotoMapper;
import ru.geekbrains.pictureapp.data.mapper.PhotoMapperImpl;
import ru.geekbrains.pictureapp.data.mapper.ThemeMapper;
import ru.geekbrains.pictureapp.data.mapper.ThemeMapperImpl;

@Module
public interface MapperModule {

    @Singleton @Binds PhotoMapper provideEntityPhotoMapper(final PhotoMapperImpl entityPhotoMapper);

    @Singleton @Binds ThemeMapper provideThemeMapper(final ThemeMapperImpl themeMapperImpl);
}
