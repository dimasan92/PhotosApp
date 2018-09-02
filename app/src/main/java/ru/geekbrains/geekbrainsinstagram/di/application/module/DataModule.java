package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.database.InstagramDatabase;
import ru.geekbrains.data.photos.PhotosDao;
import ru.geekbrains.data.photos.PhotosRepository;
import ru.geekbrains.data.settings.SettingsRepository;
import ru.geekbrains.domain.repository.IPhotosRepository;
import ru.geekbrains.domain.repository.ISettingsRepository;

@Module
public abstract class DataModule {

    private static final String DB_NAME = "instagram_database";

    @Singleton
    @Provides
    static InstagramDatabase provideDatabase(Context appContext) {
        return Room
                .databaseBuilder(appContext, InstagramDatabase.class, DB_NAME)
                .build();
    }

    @Singleton
    @Provides
    static PhotosDao providePhotosDao(InstagramDatabase database) {
        return database.photosDao();
    }

    @Singleton
    @Binds
    abstract ISettingsRepository provideSettingsRepository(SettingsRepository repository);

    @Singleton
    @Binds
    abstract IPhotosRepository providePhotosRepository(PhotosRepository repository);
}
