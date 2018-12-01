package ru.geekbrains.pictureapp.presentation.di.application.modules;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.pictureapp.data.database.AppDatabase;
import ru.geekbrains.pictureapp.data.database.ImagesDao;
import ru.geekbrains.pictureapp.data.repository.PhotosRepositoryImpl;
import ru.geekbrains.pictureapp.data.repository.SettingsRepositoryImpl;
import ru.geekbrains.pictureapp.domain.repository.PhotosRepository;
import ru.geekbrains.pictureapp.domain.repository.SettingsRepository;

@Module
public abstract class DataModule {

    private static final String DB_NAME = "photos_app_database";

    @Named("search_photos_dir")
    @Singleton
    @Provides
    static File providesNetPhotosStorageDir(final Context context) {
        final File dir = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), "search_photos");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("File cannot create");
            }
        }
        return dir;
    }

    @Named("camera_photos_dir")
    @Singleton
    @Provides
    static File providesCameraPhotosStorageDir(final Context context) {
        final File dir = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), "camera_photos");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("File cannot create");
            }
        }
        return dir;
    }

    @Singleton
    @Provides
    static AppDatabase provideDatabase(final Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, DB_NAME)
                .build();
    }

    @Singleton
    @Provides
    static ImagesDao providePhotosDao(final AppDatabase database) {
        return database.getImagesDao();
    }

    @Singleton
    @Binds
    abstract SettingsRepository provideSettingsRepository(final SettingsRepositoryImpl repository);

    @Singleton
    @Binds
    abstract PhotosRepository providePhotosRepository(final PhotosRepositoryImpl repository);
}
