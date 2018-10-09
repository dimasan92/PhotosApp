package ru.geekbrains.geekbrainsinstagram.di.activity;

public interface ActivityComponent<A> {

//    FragmentComponent getFragmentComponent();

    void inject(A activity);
}

