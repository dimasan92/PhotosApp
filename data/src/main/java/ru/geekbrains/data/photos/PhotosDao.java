package ru.geekbrains.data.photos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import ru.geekbrains.data.photos.entities.FavoritePhotoEntity;

@Dao
public interface PhotosDao {

    @Query("SELECT * FROM FavoritePhotoEntity")
    List<FavoritePhotoEntity> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addFavoritePhoto(final FavoritePhotoEntity entity);

    @Delete
    void deleteFavoritePhoto(final FavoritePhotoEntity entity);
}
