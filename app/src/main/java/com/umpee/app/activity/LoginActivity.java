package com.umpee.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.umpee.app.R;
import com.umpee.app.cache.Cache;
import com.umpee.app.custom.Const;
import com.umpee.app.custom.CustomActivity;
import com.umpee.app.model.ModelUser;
import com.umpee.app.network.NetApi;
import com.umpee.app.network.Network;
import com.umpee.app.utils.ValidChecker;
import com.yanzhenjie.nohttp.tools.NetUtil;

public class LoginActivity extends CustomActivity implements View.OnClickListener {

    protected TextView textRole;
    protected EditText editEmail;
    protected EditText editPassword;
    protected Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        setupActionbar("Login", true);
        initView();
        renderView();
    }

    private void renderView() {
        String title = getString(R.string.string_3rd_umpire);
        if (getUser().umpire == Const.UMPIRE) {
            title = getString(R.string.string_umpire);
        }

        if (!TextUtils.isEmpty(getUser().email)) editEmail.setText(getUser().email);
        textRole.setText(title);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonLogin) {
            ValidChecker.Out<String> value = new ValidChecker.Out<>();
            if (!ValidChecker.checkEmail(editEmail, value)) return;
            String email = value.get();
            if (!ValidChecker.checkEditText(editPassword, value)) return;
            String password = value.get();

            String ip = getUser().ip;
            final int umpire = getUser().umpire;
            Network.login(email, password, ip, umpire, new Network.AuthListener() {
                @Override
                public void onGot(ModelUser result) {
                    Cache.setUser(result);
                    Cache.getUser().save();

                    if (umpire == Const.UMPIRE) {
                        startServer();
                        restartActivity(UmpireActivity.class);
                    } else {
                        restartActivity(UmpireThirdActivity.class);
                    }
                }
            });
        }
    }

    private void startServer() {
    }

    private void initView() {
        textRole = (TextView) findViewById(R.id.textRole);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(LoginActivity.this);
    }
}
