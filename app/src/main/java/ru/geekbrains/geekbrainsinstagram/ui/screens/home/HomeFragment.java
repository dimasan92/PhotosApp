package ru.geekbrains.geekbrainsinstagram.ui.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.base.BaseFragment;
import ru.geekbrains.geekbrainsinstagram.util.IActivityUtils;
import ru.geekbrains.geekbrainsinstagram.util.IFragmentUtils;

public final class HomeFragment extends BaseFragment implements IFragmentUtils.EventHandler {

    @Inject
    IActivityUtils activityUtils;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        activityUtils.setupToolbar(view.findViewById(R.id.home_toolbar));

        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getChildFragmentManager(),
                getResources().getStringArray(R.array.home_tabs));

        ViewPager viewPager = view.findViewById(R.id.home_pager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.home_tabs);
        tabLayout.setupWithViewPager(viewPager);



        return view;
    }

    @Override
    public void setFabListener(View.OnClickListener listener) {

    }

    private void inject() {
        MainApplication.getApp().getComponentsManager().getFragmentComponent().inject(this);
    }

}