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

    @ColumnInfo(name = "is_favorite")
    private final boolean isFavorite;

    public FavoritePhotoEntity(@NonNull String id, boolean isFavorite) {
        this.id = id;
        this.isFavorite = isFavorite;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
