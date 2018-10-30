package ru.geekbrains.geekbrainsinstagram.ui.navigator;

@FunctionalInterface
public interface BackStackListener {

    void backToScreen(Screens screen);
}
