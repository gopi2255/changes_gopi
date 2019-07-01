package com.umpee.app.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class DialogUtils {
    public interface OnOkayCancelListener {
        void onOkay();

        void onCancel();
    }

    public static void showOkayCancelDialog(Context context, String title, String message, String textOkay, String textCancel, final OnOkayCancelListener listener) {

        MaterialDialog dlg = new MaterialDialog.Builder(context).title(title).content(message).positiveText(textOkay).negativeText(textCancel).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (listener != null) {
                    listener.onOkay();
                }
            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        }).build();
        dlg.show();
    }

    public static void showOkayDialog(Context context, String title, String message, String textOkay, final OnOkayCancelListener listener) {
        MaterialDialog dlg = new MaterialDialog.Builder(context).title(title).content(message).positiveText(textOkay).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (listener != null) {
                    listener.onOkay();
                }
            }
        }).build();
        dlg.show();
    }

}
