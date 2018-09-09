package ru.geekbrains.data.photos.personalphotos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public final class FavoritePhotoEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private final String id;

    public FavoritePhotoEntity(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }
}
