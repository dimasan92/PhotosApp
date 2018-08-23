package ru.geekbrains.geekbrainsinstagram.ui.cameragallery;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class CameraPhotoAdapter extends RecyclerView.Adapter<CameraPhotoAdapter.PhotoHolder> {

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PhotoHolder extends RecyclerView.ViewHolder{

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
