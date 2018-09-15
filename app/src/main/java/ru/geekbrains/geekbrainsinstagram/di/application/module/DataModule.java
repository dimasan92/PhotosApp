package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.database.InstagramDatabase;
import ru.geekbrains.data.photos.PhotosDao;
import ru.geekbrains.data.photos.PhotosRepositoryImpl;
import ru.geekbrains.data.settings.SettingsRepositoryImpl;
import ru.geekbrains.domain.repository.PhotosRepository;
import ru.geekbrains.domain.repository.SettingsRepository;

@Module
public abstract class DataModule {

    private static final String DB_NAME = "instagram_database";

    @Singleton
    @Provides
    static InstagramDatabase provideDatabase(Context context) {
        return Room
                .databaseBuilder(context, InstagramDatabase.class, DB_NAME)
                .build();
    }

    @Singleton
    @Provides
    static PhotosDao providePhotosDao(InstagramDatabase database) {
        return database.photosDao();
    }

    @Singleton
    @Binds
    abstract SettingsRepository provideSettingsRepository(SettingsRepositoryImpl repository);

    @Singleton
    @Binds
    abstract PhotosRepository providePhotosRepository(PhotosRepositoryImpl repository);
}
