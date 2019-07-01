package com.umpee.app.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.umpee.app.R;
import com.umpee.app.custom.Const;
import com.umpee.app.custom.CustomActivity;
import com.yanzhenjie.nohttp.tools.NetUtil;

public class SelectRoleActivity extends CustomActivity implements View.OnClickListener {


    protected TextView text3rdUmpire;
    protected View buttonAdmin;
    protected View button3rdUmpire;
    protected View buttonUmpire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_select_role);
        setupActionbar("Select your role", false);
        initView();
        getUser().ip = NetUtil.getLocalIPAddress();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAdmin) {
            makeToast("Coming soon.");
        } else if (view.getId() == R.id.button3rdUmpire) {
            getUser().umpire = Const.UMPIRE_3RD;
            startActivity(LoginActivity.class);
        } else if (view.getId() == R.id.buttnUmpire) {
            getUser().umpire = Const.UMPIRE;
            startActivity(LoginActivity.class);
        }
    }

    private void initView() {
        text3rdUmpire = (TextView) findViewById(R.id.text3rdUmpire);
        //noinspection deprecation
        text3rdUmpire.setText(Html.fromHtml(getString(R.string.string_3rd_umpire)));
        buttonAdmin = findViewById(R.id.buttonAdmin);
        buttonAdmin.setOnClickListener(SelectRoleActivity.this);
        button3rdUmpire = findViewById(R.id.button3rdUmpire);
        button3rdUmpire.setOnClickListener(SelectRoleActivity.this);
        buttonUmpire = findViewById(R.id.buttnUmpire);
        buttonUmpire.setOnClickListener(SelectRoleActivity.this);
    }
}
