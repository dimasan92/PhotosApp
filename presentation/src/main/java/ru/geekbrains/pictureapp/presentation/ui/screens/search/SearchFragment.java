package ru.geekbrains.pictureapp.presentation.ui.screens.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import javax.inject.Inject;

import ru.geekbrains.pictureapp.R;
import ru.geekbrains.pictureapp.presentation.MainApplication;
import ru.geekbrains.pictureapp.presentation.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.Screens;

public final class SearchFragment extends Fragment {

    @Inject ContainerToContentMediator mediator;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getHomeComponent()
                .inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_pictures_container, container, false);

        initView(layout);
        initToolbar(layout);

        return layout;
    }

    private void initView(final View layout) {
        final SearchPagerAdapter adapter = new SearchPagerAdapter(getChildFragmentManager(),
                Objects.requireNonNull(getContext()));

        final ViewPager viewPager = layout.findViewById(R.id.vp_pictures);
        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = layout.findViewById(R.id.tl_pictures);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initToolbar(final View layout) {
        final Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_pictures_title);
        mediator.setupToolbar(toolbar, Screens.HOME_SCREEN);
    }
}
