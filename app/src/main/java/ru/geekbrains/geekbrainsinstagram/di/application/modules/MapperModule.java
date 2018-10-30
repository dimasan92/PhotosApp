package ru.geekbrains.geekbrainsinstagram.di.application.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.data.mapper.PhotoMapper;
import ru.geekbrains.data.mapper.PhotoMapperImpl;
import ru.geekbrains.data.mapper.ThemeMapper;
import ru.geekbrains.data.mapper.ThemeMapperImpl;

@Module
public interface MapperModule {

    @Singleton @Binds PhotoMapper provideEntityPhotoMapper(final PhotoMapperImpl entityPhotoMapper);

    @Singleton @Binds ThemeMapper provideThemeMapper(final ThemeMapperImpl themeMapperImpl);
}
