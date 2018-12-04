package ru.geekbrains.pictureapp.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.data.database.entities.FavoriteEntity;

@Dao
public interface ImagesDao {

    @Query("SELECT * FROM favorites")
    Single<List<FavoriteEntity>> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addFavorite(final FavoriteEntity entity);

    @Delete
    void deleteFavorite(final FavoriteEntity entity);
}
