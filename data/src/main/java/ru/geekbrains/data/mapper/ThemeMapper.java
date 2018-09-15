package ru.geekbrains.data.mapper;

import ru.geekbrains.domain.model.AppThemeModel;

public interface ThemeMapper {

    String domainToData(final AppThemeModel appTheme);

    AppThemeModel dataToDomain(final String appTheme);
}
