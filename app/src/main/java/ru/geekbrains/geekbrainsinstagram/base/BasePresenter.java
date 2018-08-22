package ru.geekbrains.geekbrainsinstagram.base;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends BaseContract.View>
        implements BaseContract.Presenter<V> {

    protected V view;

    protected final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        viewDestroyed();
        clearSubscriptions();
    }

    private void viewDestroyed() {
        view = null;
    }

    private void clearSubscriptions() {
        disposables.clear();
    }
}
