package ru.geekbrains.geekbrainsinstagram.ui.mediator;

import android.view.View;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;

@FragmentScope
public final class FragmentToFragmentMediator implements IFragmentToFragmentMediator {

    private EventHandler handler;

    @Inject
    FragmentToFragmentMediator() {
    }

    @Override
    public void init(EventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setFabListener(View.OnClickListener listener) {
        handler.setFabListener(listener);
    }

    @Override
    public void showNotifyingMessage(int messageId, int duration) {
        handler.makeNotifyingMessage(messageId, duration);
    }
}
