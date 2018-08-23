package ru.geekbrains.data.innerstoragephotos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface InnerStoragePhotosDao {

    @Query("SELECT * FROM innerstoragephotoentity")
    Flowable<List<InnerStoragePhotoEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(InnerStoragePhotoEntity entity);
}
