package ru.geekbrains.geekbrainsinstagram.ui.screens.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;

public final class SearchFragment extends Fragment {

    @Inject ContainerToContentMediator mediator;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @NonNull @Override public View onCreateView(@NonNull LayoutInflater inflater,
                                                @Nullable ViewGroup container,
                                                @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search_container, container, false);

        inject();
        initView(layout);

        return layout;
    }

    private void inject() {
        MainApplication.getApp()
                .getComponentsManager()
                .getHomeComponent()
                .inject(this);
    }

    private void initView(final View layout) {
        final Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_search_title);
        mediator.setupToolbar(toolbar, Screens.HOME_SCREEN);

        final SearchPagerAdapter adapter = new SearchPagerAdapter(getChildFragmentManager(),
                Objects.requireNonNull(getContext()));

        final ViewPager viewPager = layout.findViewById(R.id.vp_search);
        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = layout.findViewById(R.id.tl_search);
        tabLayout.setupWithViewPager(viewPager);
    }
}
