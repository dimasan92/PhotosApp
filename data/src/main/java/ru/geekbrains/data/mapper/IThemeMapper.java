package ru.geekbrains.data.mapper;

import ru.geekbrains.domain.model.AppThemeModel;

public interface IThemeMapper {

    String domainToData(AppThemeModel appTheme);

    AppThemeModel dataToDomain(String appTheme);
}
