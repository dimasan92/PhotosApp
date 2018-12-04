package ru.geekbrains.pictureapp.data.mapper;

import ru.geekbrains.pictureapp.domain.model.AppThemeModel;

public interface ThemeMapper {

    String mapFromModel(final AppThemeModel appThemeModel);

    AppThemeModel mapToModel(final String appTheme);
}
