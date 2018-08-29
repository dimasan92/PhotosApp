package ru.geekbrains.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.geekbrains.data.photos.PhotosDao;
import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;

@Database(entities = {PersonalPhotoEntity.class}, version = 1, exportSchema = false)
public abstract class InstagramDatabase extends RoomDatabase {

    public abstract PhotosDao innerStoragePhotosDao();
}
