package ru.geekbrains.pictureapp.presentation.util;

import java.util.List;

import ru.geekbrains.pictureapp.domain.model.ImageModel;

public interface ParseUtils {

    String[] parseObjects(final List<ImageModel> imageModels);

    List<ImageModel> parseToObjects(final String[] jsons);
}
