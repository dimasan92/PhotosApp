package ru.geekbrains.pictureapp.data.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.pictureapp.domain.model.AppThemeModel;

@Singleton
public final class ThemeMapperImpl implements ThemeMapper {

    private static final String RED_THEME = "red_application_theme";
    private static final String BLUE_THEME = "blue_application_theme";
    private static final String GREEN_THEME = "green_application_theme";

    @Inject
    ThemeMapperImpl() {
    }

    @Override
    public String mapFromModel(final AppThemeModel appThemeModel) {
        switch (appThemeModel) {
            case RED_THEME:
                return RED_THEME;
            case BLUE_THEME:
                return BLUE_THEME;
            case GREEN_THEME:
                return GREEN_THEME;
            default:
                return BLUE_THEME;
        }
    }

    @Override
    public AppThemeModel mapToModel(final String appTheme) {
        switch (appTheme) {
            case RED_THEME:
                return AppThemeModel.RED_THEME;
            case BLUE_THEME:
                return AppThemeModel.BLUE_THEME;
            case GREEN_THEME:
                return AppThemeModel.GREEN_THEME;
            default:
                return AppThemeModel.BLUE_THEME;
        }
    }
}
