package ru.geekbrains.pictureapp.presentation.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.pictureapp.domain.model.ImageModel;

@Singleton
public final class ParseUtilsImpl implements ParseUtils {

    private final Gson gson;

    @Inject
    ParseUtilsImpl(final Gson gson) {
        this.gson = gson;
    }

    @Override
    public String[] parseObjects(final List<ImageModel> imageModels) {
        final String[] jsons = new String[imageModels.size()];
        for (int i = 0; i < imageModels.size(); i++) {
            final String json = gson.toJson(imageModels.get(i));
            jsons[i] = json;
        }
        return jsons;
    }

    @Override
    public List<ImageModel> parseToObjects(final String[] jsons) {
        final List<ImageModel> imageModels = new ArrayList<>(jsons.length);
        try {
            for (String json : jsons) {
                final ImageModel imageModel = gson.fromJson(json, ImageModel.class);
                imageModels.add(imageModel);
            }
        } catch (final JsonSyntaxException ignored) {}
        return imageModels;
    }
}
