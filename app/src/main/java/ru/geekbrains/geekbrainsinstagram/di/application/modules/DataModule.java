package ru.geekbrains.geekbrainsinstagram.di.application.modules;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.database.AppDatabase;
import ru.geekbrains.data.database.PhotosDao;
import ru.geekbrains.data.photos.PhotosRepositoryImpl;
import ru.geekbrains.data.settings.SettingsRepositoryImpl;
import ru.geekbrains.domain.repository.PhotosRepository;
import ru.geekbrains.domain.repository.SettingsRepository;

@Module
public abstract class DataModule {

    private static final String DB_NAME = "geek_brains_photos_database";
    private static final String appPath = "geek_brains_photos" + File.separator;

    @Named("search_photos_dir") @Singleton @Provides static File providesNetPhotosStorageDir(final Context context) {
        final File dir = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), appPath + "search_photos");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("File cannot create");
            }
        }
        return dir;
    }

    @Named("camera_photos_dir") @Singleton @Provides static File providesCameraPhotosStorageDir(final Context context) {
        final File dir = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), appPath + "camera_photos");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("File cannot create");
            }
        }
        return dir;
    }

    @Singleton @Provides static AppDatabase provideDatabase(final Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, DB_NAME)
                .build();
    }

    @Singleton @Provides static PhotosDao providePhotosDao(final AppDatabase database) {
        return database.photosDao();
    }

    @Singleton @Binds abstract SettingsRepository provideSettingsRepository(final SettingsRepositoryImpl repository);

    @Singleton @Binds abstract PhotosRepository providePhotosRepository(final PhotosRepositoryImpl repository);
}
