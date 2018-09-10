package ru.geekbrains.geekbrainsinstagram.ui.screens.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import ru.geekbrains.geekbrainsinstagram.di.ContentDisposer;
import ru.geekbrains.geekbrainsinstagram.ui.screens.databasephotos.DatabasePhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.netphotos.NetPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.personalphotos.PersonalPhotosFragment;

public final class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabsTitles;
    private final ContentDisposer disposer;

    HomeFragmentPagerAdapter(FragmentManager fragmentManager, String[] titles,
                             ContentDisposer disposer) {
        super(fragmentManager);
        this.tabsTitles = titles;
        this.disposer = disposer;
    }

    @Override
    public Fragment getItem(int position) {
        disposer.disposeContent();
        switch (position) {
            case 0:
                return DatabasePhotosFragment.newInstance();
            case 1:
                return NetPhotosFragment.newInstance();
            case 2:
                return PersonalPhotosFragment.newInstance();
            default:
                throw new IllegalArgumentException("Illegal position " + position);
        }
    }

    @Override
    public int getCount() {
        return tabsTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabsTitles[position];
    }
}
