package ru.geekbrains.pictureapp.data.mapper;

import ru.geekbrains.pictureapp.domain.model.AppThemeModel;

public interface ThemeMapper {

    String domainToData(final AppThemeModel appTheme);

    AppThemeModel dataToDomain(final String appTheme);
}
