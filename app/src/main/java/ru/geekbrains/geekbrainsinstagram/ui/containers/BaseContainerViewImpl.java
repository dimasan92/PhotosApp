package ru.geekbrains.geekbrainsinstagram.ui.containers;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.R;

public abstract class BaseContainerViewImpl<V extends BaseContainerPresenter.View, P extends BaseContainerPresenter<V>>
        extends AppCompatActivity
        implements BaseContainerPresenter.View {

    @Inject
    protected P presenter;

    private boolean isViewSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        attachView();
        isViewSet = true;
        presenter.beforeOnCreate();

        super.onCreate(savedInstanceState);

        setupView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isViewSet) {
            attachView();
            isViewSet = true;
        }
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
        isViewSet = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            release();
        }
    }

    @Override
    public void setTheme(AppThemeModel theme) {
        switch (theme) {
            case RED_THEME:
                setTheme(R.style.RedAppTheme);
                break;
            case BLUE_THEME:
                setTheme(R.style.BlueAppTheme);
                break;
            case GREEN_THEME:
                setTheme(R.style.GreenAppTheme);
                break;
        }
    }

    protected abstract void release();

    protected abstract void inject();

    protected abstract void attachView();

    protected abstract void setupView();
}
