package com.umpee.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.umpee.app.R;
import com.umpee.app.cache.Cache;
import com.umpee.app.custom.CustomActivity;
import com.umpee.app.model.ModelUser;
import com.umpee.app.model.ModelVideo;
import com.umpee.app.network.Network;
import com.umpee.app.server.ServiceReceiver;
import com.umpee.app.utils.ObjectUtils;

import java.util.ArrayList;


public class UmpireThirdActivity extends CustomActivity implements View.OnClickListener {
    protected View buttonUmpire1;
    protected View buttonUmpire;
    protected View buttonLBW;
    protected View buttonSnick;
    protected Button buttonLogout;
    protected Button buttonFinish;

    ArrayList<ModelUser> umpireList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_umpire_third);
        setupActionbar("3rd Umpire Home", false);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        Network.getUmpires(getUser().token, new Network.UmpiresListener() {
            @Override
            public void onGot(ArrayList<ModelUser> result) {
                umpireList = result;
                Cache.mUmpireList = umpireList;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonUmpire1) {
            if (ObjectUtils.isEmpty(umpireList)) {
                makeToast("No umpires");
                return;
            }
            ModelUser user = umpireList.get(0);
            Network.getVideo(user.getPublicURL(), getUser().token, new Network.VideoListener() {
                @Override
                public void onGot(ModelVideo result) {
                    startActivity(PlayerActivity.newIntent(mContext, "umpire", result.url));
                }
            });

        } else if (view.getId() == R.id.buttonUmpire) {
            if (ObjectUtils.isEmpty(umpireList)) {
                makeToast("No umpires");
                return;
            }
            if (umpireList.size() < 2) {
                makeToast("Not exist 2nd umpire");
                return;
            }

            ModelUser user = umpireList.get(1);
            Network.getVideo(user.getPublicURL(), getUser().token, new Network.VideoListener() {
                @Override
                public void onGot(ModelVideo result) {
                    startActivity(PlayerActivity.newIntent(mContext, "umpire", result.url));
                }
            });
        } else if (view.getId() == R.id.buttonLBW) {
            //makeToast("Coming soon.");
            Intent i = new Intent(this, UmpireThirdOptionsActivity.class);
            startActivity(i);
        } else if (view.getId() == R.id.buttonSnick) {
            makeToast("Coming soon.");
        } else if (view.getId() == R.id.buttonLogout) {
            getUser().logout();
            restartActivity(SelectRoleActivity.class);
        } else if (view.getId() == R.id.buttonFinish) {
            finish();
        }
    }

    private void initView() {
        buttonUmpire1 = findViewById(R.id.buttonUmpire1);
        buttonUmpire1.setOnClickListener(UmpireThirdActivity.this);
        buttonUmpire = findViewById(R.id.buttonUmpire);
        buttonUmpire.setOnClickListener(UmpireThirdActivity.this);
        buttonLBW = findViewById(R.id.buttonLBW);
        buttonLBW.setOnClickListener(UmpireThirdActivity.this);
        buttonSnick = findViewById(R.id.buttonSnick);
        buttonSnick.setOnClickListener(UmpireThirdActivity.this);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(UmpireThirdActivity.this);
        buttonFinish = (Button) findViewById(R.id.buttonFinish);
        buttonFinish.setOnClickListener(UmpireThirdActivity.this);
    }
}
