package ru.geekbrains.pictureapp.presentation.ui.screens.search;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinesearch.OnlineSearchFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedsearch.SavedSearchFragment;

public final class SearchPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles;

    SearchPagerAdapter(final FragmentManager fragmentManager,
                       final Context appContext) {
        super(fragmentManager);
        tabTitles = appContext.getResources().getStringArray(R.array.home_tabs);
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OnlineSearchFragment.newInstance();
            case 1:
                return SavedSearchFragment.newInstance();
            default:
                throw new IllegalArgumentException("Illegal position " + position);
        }
    }

    @Override public int getCount() {
        return tabTitles.length;
    }

    @Override public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
