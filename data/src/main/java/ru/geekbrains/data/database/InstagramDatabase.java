package ru.geekbrains.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.geekbrains.data.innerstoragephotos.InnerStoragePhotoEntity;
import ru.geekbrains.data.innerstoragephotos.InnerStoragePhotosDao;

@Database(entities = {InnerStoragePhotoEntity.class}, version = 1, exportSchema = false)
public abstract class InstagramDatabase extends RoomDatabase {

    public abstract InnerStoragePhotosDao innerStoragePhotosDao();
}
