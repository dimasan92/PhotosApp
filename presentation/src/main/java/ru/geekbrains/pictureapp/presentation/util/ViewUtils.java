package ru.geekbrains.pictureapp.presentation.util;


import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public interface ViewUtils {

    void showDeleteImageDialog(final Context context, final OnClickListener confirmClick);
}
