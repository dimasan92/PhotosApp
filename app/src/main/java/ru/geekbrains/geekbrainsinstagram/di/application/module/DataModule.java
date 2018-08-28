package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.database.InstagramDatabase;
import ru.geekbrains.data.mapper.IEntityMapper;
import ru.geekbrains.data.photos.PhotosDao;
import ru.geekbrains.data.photos.PhotosRepository;
import ru.geekbrains.data.settings.SettingsRepository;
import ru.geekbrains.data.util.IPrefUtils;
import ru.geekbrains.domain.repository.IPhotosRepository;
import ru.geekbrains.domain.repository.ISettingsRepository;

@Module
public final class DataModule {

    private static final String DB_NAME = "instagram_database";

    private final InstagramDatabase database;

    public DataModule(Context appContext) {
        database = Room
                .databaseBuilder(appContext, InstagramDatabase.class, DB_NAME)
                .build();
    }

    @Singleton
    @Provides
    ISettingsRepository provideSettingsRepository(final IPrefUtils prefUtils) {
        return new SettingsRepository(prefUtils);
    }

    @Singleton
    @Provides
    IPhotosRepository providePhotosRepository(final PhotosDao dao,
                                              final IEntityMapper mapper) {
        return new PhotosRepository(dao, mapper);
    }

    @Singleton
    @Provides
    PhotosDao providePhotosDao() {
        return database.innerStoragePhotosDao();
    }
}
