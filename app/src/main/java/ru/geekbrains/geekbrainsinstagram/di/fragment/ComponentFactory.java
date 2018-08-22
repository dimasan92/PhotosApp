package ru.geekbrains.geekbrainsinstagram.di.fragment;

public final class ComponentFactory {

    private ComponentFactory(){
    }

    public static FragmentComponent createFragmentComponent(){
        return DaggerFragmentComponent.create();
    }
}
