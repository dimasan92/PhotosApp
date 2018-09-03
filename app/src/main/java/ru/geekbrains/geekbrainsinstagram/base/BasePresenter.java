package ru.geekbrains.geekbrainsinstagram.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class BasePresenter<V extends IBasePresenter.IView> implements IBasePresenter<V> {

    protected V view;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        viewDestroyed();
        clearSubscriptions();
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected Consumer<Throwable> getDefaultErrorHandler() {
        return Throwable::printStackTrace;
    }

    private void viewDestroyed() {
        view = null;
    }

    private void clearSubscriptions() {
        disposables.clear();
    }
}
