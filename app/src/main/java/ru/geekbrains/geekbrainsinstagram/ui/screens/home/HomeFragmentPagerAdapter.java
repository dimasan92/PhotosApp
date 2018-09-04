package ru.geekbrains.geekbrainsinstagram.ui.screens.home;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public final class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabsTitles;

    public HomeFragmentPagerAdapter(FragmentManager fragmentManager, String[] titles) {
        super(fragmentManager);
        this.tabsTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return tabsTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return super.getPageTitle(position);
    }
}
