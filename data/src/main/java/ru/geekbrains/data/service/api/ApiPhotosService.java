package ru.geekbrains.data.service.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.geekbrains.data.service.model.RandomPhotoModel;

public interface ApiPhotosService {

    @GET("photos/random?client_id=f38bc891e9bf954852353fdb0409c05ec9e4a17c6abdce9bbc176992f8fecf40&orientation=squarish")
    Single<List<RandomPhotoModel>> getSquarishRandomQueryPhoto(@Query("query") final String query,
                                                               @Query("count") final int count);
}