package ru.geekbrains.geekbrainsinstagram.ui.screens.databasephotos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.geekbrains.geekbrainsinstagram.R;

public final class DatabasePhotosFragment extends Fragment {

    public static DatabasePhotosFragment newInstance() {
        return new DatabasePhotosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_database_photos, container, false);
    }
}
