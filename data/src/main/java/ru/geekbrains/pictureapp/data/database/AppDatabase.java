package ru.geekbrains.pictureapp.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.geekbrains.pictureapp.data.database.entities.FavoriteEntity;

@Database(entities = {FavoriteEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ImagesDao getImagesDao();
}
