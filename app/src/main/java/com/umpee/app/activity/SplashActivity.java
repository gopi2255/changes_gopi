package com.umpee.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.umpee.app.R;
import com.umpee.app.custom.CustomActivity;
import com.umpee.app.model.ModelUser;
import com.umpee.app.network.Network;
import com.umpee.app.utils.Utils;

public class SplashActivity extends CustomActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getUser().hasAuthorized()) {
            String token = getUser().token;
            String ip = Utils.getIPAddress(this);
            if (!TextUtils.isEmpty(ip)) {
                getUser().ip = ip;
                getUser().save();

                Network.updateInfo(token, ip, new Network.AuthListener() {
                    @Override
                    public void onGot(ModelUser result) {
                    }
                });
            }
        }
        run();
    }

    private void run() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                if (!getUser().hasAuthorized()) {
                    startActivity(SelectRoleActivity.class);
                } else {
                    if (getUser().isUmpire()) {
                        startActivity(UmpireActivity.class);
                    } else {
                        startActivity(UmpireThirdActivity.class);
                    }
                }
            }
        }, 1000);

    }
}
