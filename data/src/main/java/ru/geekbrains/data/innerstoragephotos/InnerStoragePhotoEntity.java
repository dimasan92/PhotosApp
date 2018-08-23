package ru.geekbrains.data.innerstoragephotos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InnerStoragePhotoEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "uri")
    private String uri;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    public InnerStoragePhotoEntity(String id, String uri, boolean isFavorite) {
        this.id = id;
        this.uri = uri;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
