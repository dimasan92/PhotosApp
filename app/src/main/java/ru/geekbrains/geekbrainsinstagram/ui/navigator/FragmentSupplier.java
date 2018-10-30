package ru.geekbrains.geekbrainsinstagram.ui.navigator;

import androidx.fragment.app.Fragment;

@FunctionalInterface
public interface FragmentSupplier {

    Fragment supplyFragment();
}
