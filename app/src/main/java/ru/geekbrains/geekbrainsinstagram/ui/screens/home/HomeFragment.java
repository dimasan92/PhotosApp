package ru.geekbrains.geekbrainsinstagram.ui.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IFragmentToFragmentMediator;

public final class HomeFragment extends BaseFragment implements IFragmentToFragmentMediator.EventHandler {

    @Inject
    IActivityToFragmentMediator activityToFragmentMediator;

    @Inject
    IFragmentToFragmentMediator fragmentToFragmentMediator;

    private FloatingActionButton homeFab;
    private CoordinatorLayout homeLayout;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inject();
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fragmentToFragmentMediator.init(this);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.appbar_home_title);
        activityToFragmentMediator.setupToolbar(toolbar);

        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getChildFragmentManager(),
                getResources().getStringArray(R.array.home_tabs),
                () -> MainApplication.getApp().getComponentsManager().releaseChildFragmentComponent());

        ViewPager viewPager = view.findViewById(R.id.home_pager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(createPageChangeListener());

        TabLayout tabLayout = view.findViewById(R.id.home_tabs);
        tabLayout.setupWithViewPager(viewPager);

        homeFab = view.findViewById(R.id.home_fab);
        homeFab.hide();
        homeLayout = view.findViewById(R.id.home_layout);

        return view;
    }

    @Override
    public void setFabListener(View.OnClickListener listener) {
        homeFab.setOnClickListener(listener);
    }

    @Override
    public void makeNotifyingMessage(@StringRes int messageId, int duration) {
        Snackbar.make(homeLayout, messageId, Snackbar.LENGTH_SHORT).show();
    }

    private void inject() {
        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
    }

    private ViewPager.OnPageChangeListener createPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                    case 1:
                        homeFab.hide();
                        break;
                    case 2:
                        homeFab.show();
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal position " + position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
    }
}
