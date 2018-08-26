package ru.geekbrains.data.photos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import ru.geekbrains.data.photos.personalphotos.PersonalPhotoEntity;

@Dao
public interface PhotosDao {

    @Query("SELECT * FROM PersonalPhotoEntity")
    Flowable<List<PersonalPhotoEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPersonalPhoto(PersonalPhotoEntity entity);
}
