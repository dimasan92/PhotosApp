package ru.geekbrains.geekbrainsinstagram.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class BasePresenterImpl<V extends BasePresenter.View> implements BasePresenter<V> {

    protected V view;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        dettachView();
        clearSubscriptions();
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected Consumer<Throwable> getDefaultErrorHandler() {
        return Throwable::printStackTrace;
    }

    private void dettachView() {
        view = null;
    }

    private void clearSubscriptions() {
        disposables.clear();
    }
}
