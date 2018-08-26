package ru.geekbrains.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;
import ru.geekbrains.data.photos.PhotosDao;

@Database(entities = {PersonalPhotoEntity.class}, version = 1, exportSchema = false)
public abstract class InstagramDatabase extends RoomDatabase {

    public abstract PhotosDao innerStoragePhotosDao();
}
