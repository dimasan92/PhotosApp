package ru.geekbrains.data.photos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import ru.geekbrains.data.photos.personalphotos.FavoritePhotoEntity;

@Dao
public interface PhotosDao {

    @Query("SELECT * FROM FavoritePhotoEntity")
    List<FavoritePhotoEntity> getAllFavoritePhotos();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updatePersonalPhoto(FavoritePhotoEntity entity);

    @Delete
    void deletePersonalPhoto(FavoritePhotoEntity entity);
}
