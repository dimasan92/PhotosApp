package ru.geekbrains.pictureapp.presentation.util;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.pictureapp.domain.model.PhotoModel;

@Singleton
public final class ParseUtilsImpl implements ParseUtils {

    private final Gson gson;

    @Inject
    ParseUtilsImpl(final Gson gson) {
        this.gson = gson;
    }

    @Override
    public String[] parseObjects(List<PhotoModel> photoModels) {
        final String[] jsons = new String[photoModels.size()];
        for (int i = 0; i < photoModels.size(); i++) {
            final String json = gson.toJson(photoModels.get(i));
            jsons[i] = json;
        }
        return jsons;
    }

    @Override
    public List<PhotoModel> parseToObjects(String[] jsons) {
        final List<PhotoModel> photoModels = new ArrayList<>(jsons.length);
        for (String json : jsons) {
            final PhotoModel photoModel = gson.fromJson(json, PhotoModel.class);
            photoModels.add(photoModel);
        }
        return photoModels;
    }
}
