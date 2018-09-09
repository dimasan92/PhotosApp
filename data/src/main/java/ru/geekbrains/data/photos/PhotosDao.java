package ru.geekbrains.data.photos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;
import ru.geekbrains.data.photos.personalphotos.FavoritePhotoEntity;

@Dao
public interface PhotosDao {

    @Query("SELECT * FROM FavoritePhotoEntity")
    List<FavoritePhotoEntity> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addFavoritePhoto(FavoritePhotoEntity entity);

    @Delete
    void deleteFavoritePhoto(FavoritePhotoEntity entity);
}
