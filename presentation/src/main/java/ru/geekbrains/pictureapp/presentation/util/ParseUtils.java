package ru.geekbrains.pictureapp.presentation.util;

import java.util.List;

import ru.geekbrains.pictureapp.domain.model.PhotoModel;

public interface ParseUtils {

    String[] parseObjects(final List<PhotoModel> photoModels);

    List<PhotoModel> parseToObjects(final String[] jsons);
}
