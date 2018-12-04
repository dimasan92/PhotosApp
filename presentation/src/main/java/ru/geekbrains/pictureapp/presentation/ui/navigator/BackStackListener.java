package ru.geekbrains.pictureapp.presentation.ui.navigator;

@FunctionalInterface
public interface BackStackListener {

    void backToScreen(Screens screen);
}
