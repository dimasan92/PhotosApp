package ru.geekbrains.pictureapp.presentation.ui.updater;

import io.reactivex.functions.Consumer;
import ru.geekbrains.pictureapp.domain.model.ImageModel;

public interface Updater {

    void update(final ImageModel imageModel);

    void subscribe(final Consumer<Update> updater);

    void dispose();
}
