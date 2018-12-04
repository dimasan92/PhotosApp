package ru.geekbrains.pictureapp.presentation.ui.navigator;

import androidx.fragment.app.Fragment;

@FunctionalInterface
public interface FragmentSupplier {

    Fragment supplyFragment();
}
