package com.umpee.app.utils;

import android.text.TextUtils;
import android.widget.EditText;

import com.umpee.app.MyApp;
import com.umpee.app.R;


public class ValidChecker {
    public static boolean checkEditText(EditText edit, Out<String> value) {
        if (edit == null) return false;
        edit.setError(null);
        String string = edit.getText().toString();
        if (TextUtils.isEmpty(string)) {
            edit.setError(MyApp.getContext().getString(R.string.msg_field_required));
            edit.requestFocus();
            return false;
        }
        value.set(string);
        return true;
    }

    public static boolean checkEmail(EditText edit, Out<String> value) {
        if (!checkEditText(edit, value)) return false;
        String string = value.get();
        boolean res = !TextUtils.isEmpty(string) && android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches();
        if (!res) {
            edit.setError(MyApp.getContext().getString(R.string.msg_email_invalid));
            edit.requestFocus();
            return false;
        }
        return true;
    }

    public static class Out<T> {
        T s;

        public void set(T value) {
            s = value;
        }

        public T get() {
            return s;
        }

        public Out() {
        }
    }
}
