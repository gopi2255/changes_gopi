package com.umpee.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umpee.app.R;
import com.umpee.app.custom.CustomActivity;

public class MessageActivity extends CustomActivity implements View.OnClickListener {

    protected TextView textMessage;
    protected Button buttonClose;

    String result;

    public static Intent newIntent(Context context, String result) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra("param", result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_result);
        setupActionbar("3rd Umpire's Result", true);
        result = getIntent().getStringExtra("param");
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonClose) {
            finish();
        }
    }

    private void initView() {
        textMessage = (TextView) findViewById(R.id.textMessage);
        buttonClose = (Button) findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(MessageActivity.this);
        textMessage.setText("Result from 3rd Umpire is " + result);
    }
}
