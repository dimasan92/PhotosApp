package ru.geekbrains.data.photos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;
import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;

@Dao
public interface PhotosDao {

    @Query("SELECT * FROM PersonalPhotoEntity")
    Single<List<PersonalPhotoEntity>> getAllPersonalPhotos();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updatePersonalPhoto(PersonalPhotoEntity entity);

    @Delete
    void deletePersonalPhoto(PersonalPhotoEntity entity);
}
