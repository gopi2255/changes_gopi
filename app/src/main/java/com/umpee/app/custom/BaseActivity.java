package com.umpee.app.custom;

import android.os.Bundle;

import com.umpee.app.R;
import com.umpee.app.custom.CustomActivity;

public class BaseActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
}
