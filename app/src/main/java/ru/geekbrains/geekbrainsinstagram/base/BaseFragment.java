package ru.geekbrains.geekbrainsinstagram.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.geekbrains.geekbrainsinstagram.di.fragment.ComponentFactory;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentComponent;

public abstract class BaseFragment extends Fragment {

    private FragmentComponent fragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getComponent());
    }

    protected abstract void inject(FragmentComponent fragmentComponent);

    private FragmentComponent getComponent() {
        if (fragmentComponent == null) {
            fragmentComponent = ComponentFactory.createFragmentComponent();
        }
        return fragmentComponent;
    }
}
