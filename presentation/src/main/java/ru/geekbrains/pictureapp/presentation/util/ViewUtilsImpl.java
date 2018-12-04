package ru.geekbrains.pictureapp.presentation.util;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;

import androidx.appcompat.app.AlertDialog;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.pictureapp.R;

@Singleton
public class ViewUtilsImpl implements ViewUtils {

    @Inject
    ViewUtilsImpl() {
    }

    @Override
    public void showDeleteImageDialog(final Context context, final OnClickListener confirmClick) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(R.string.delete_photo_dialog_title);
        dialogBuilder.setPositiveButton(R.string.delete_photo_dialog_delete, confirmClick);
        dialogBuilder.setNegativeButton(R.string.delete_photo_dialog_cancel, (dialog, which) -> {
        });
        dialogBuilder.show();
    }
}
