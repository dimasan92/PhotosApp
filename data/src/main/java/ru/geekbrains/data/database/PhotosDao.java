package ru.geekbrains.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;
import ru.geekbrains.data.database.entities.FavoriteEntity;

@Dao
public interface PhotosDao {

    @Query("SELECT * FROM favorites") Single<List<FavoriteEntity>> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.IGNORE) void addFavorite(final FavoriteEntity entity);

    @Delete void deleteFavorite(final FavoriteEntity entity);
}
