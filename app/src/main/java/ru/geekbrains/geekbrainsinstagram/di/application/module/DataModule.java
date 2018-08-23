package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.database.InstagramDatabase;
import ru.geekbrains.data.innerstoragephotos.InnerStoragePhotosDao;
import ru.geekbrains.data.mapper.DataMapper;
import ru.geekbrains.data.photos.PhotosRepositoryImpl;
import ru.geekbrains.data.settings.SettingsRepositoryImpl;
import ru.geekbrains.data.util.PrefUtils;
import ru.geekbrains.domain.repository.PhotosRepository;
import ru.geekbrains.domain.repository.SettingsRepository;

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
    SettingsRepository providesSettingsRepository(final PrefUtils prefUtils) {
        return new SettingsRepositoryImpl(prefUtils);
    }

    @Singleton
    @Provides
    PhotosRepository providePhotosRepository(final InnerStoragePhotosDao dao,
                                             final DataMapper mapper) {
        return new PhotosRepositoryImpl(dao, mapper);
    }

    @Singleton
    @Provides
    InnerStoragePhotosDao provideInnerStoragePhotosDao() {
        return database.innerStoragePhotosDao();
    }
}
