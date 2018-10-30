package ru.geekbrains.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public final class FavoriteEntity {

    @NonNull @PrimaryKey @ColumnInfo(name = "id") private final String id;
    @NonNull @ColumnInfo(name = "file_path") private final String filePath;

    public FavoriteEntity(@NonNull final String id, @NonNull final String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    @NonNull public String getId() {
        return id;
    }

    @NonNull public String getFilePath() {
        return filePath;
    }
}
