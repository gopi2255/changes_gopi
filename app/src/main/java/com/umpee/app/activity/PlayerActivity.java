package com.umpee.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awesomeplayer.GiraffePlayer;
import com.umpee.app.R;
import com.umpee.app.cache.Cache;
import com.umpee.app.custom.CustomActivity;
import com.umpee.app.model.ModelUser;
import com.umpee.app.network.Network;
import com.umpee.app.utils.ObjectUtils;


public class PlayerActivity extends CustomActivity implements GiraffePlayer.OnErrorListener {
    GiraffePlayer player;

    boolean isErrorOccurred = false;
    private static String mUrl;
    private static String mName;
    private String mFinalUrl;

    public static Intent newIntent(Context context, String name, String url) {
        Intent intent = new Intent(context, PlayerActivity.class);
        mUrl = url;
        mName = name;
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        player = new GiraffePlayer(this);

        LinearLayout layoutOutContainer = (LinearLayout) findViewById(R.id.layoutOutContainer);
        if (TextUtils.isEmpty(mUrl) || !mUrl.contains("http")) {
            layoutOutContainer.setVisibility(View.GONE);
        } else {
            layoutOutContainer.setVisibility(View.VISIBLE);
        }
        initCustomActionBar();

        play(mUrl);
    }

    public void play(String url) {
        if (!TextUtils.isEmpty(url)) {
            mFinalUrl = url;
            if (player != null) {
                player.onDestroy();
                player = null;
            }
            createGiraffePlayer();
            setTitle(mName);
            player.setFullScreenOnly(true);
            setShowNavIcon(true);
            player.play(url);
        }
    }

    private void createGiraffePlayer() {
        player = new GiraffePlayer(this);
        player.onControlPanelVisibilityChange(new GiraffePlayer.OnControlPanelVisibilityChangeListener() {
            @Override
            public void change(boolean isShowing) {
                if (isShowing) {
                    findViewById(R.id.layoutCustomActionBar).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.layoutCustomActionBar).setVisibility(View.GONE);
                }
            }
        });
    }

    private void initCustomActionBar() {
        findViewById(R.id.app_video_finish).setOnClickListener(onClickListener);
        findViewById(R.id.layoutSubtitle).setVisibility(View.GONE);
        findViewById(R.id.app_video_restart).setOnClickListener(onClickListener);
        findViewById(R.id.buttonOut).setOnClickListener(onClickListener);
        findViewById(R.id.buttonNotOut).setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {

                case R.id.app_video_restart:
                    restartPlayer();
                    break;
                case R.id.app_video_finish:
                    finish();
                    break;

                case R.id.buttonOut:
                    sendOut(true);
                    break;
                case R.id.buttonNotOut:
                    sendOut(false);
                    break;
            }
        }
    };

    private void sendOut(final boolean isOut) {
        if (ObjectUtils.isEmpty(Cache.mUmpireList)) return;
        Network.sendOut(isOut, new Network.MessageListener() {
            @Override
            public void onGot(String result) {
                makeToast("Sent message " + (isOut?"OUT":"NOT OUT" )+ " to umpires");
            }
        });
    }

    private void restartPlayer() {
        if (TextUtils.isEmpty(mFinalUrl)) return;
        play(mFinalUrl);
    }

    public void startPlayer() {
        player.start();
    }


    @Override
    public void onPause() {
        super.onPause();

        if (player != null) {
            player.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {

        if (player != null) {
            player.onDestroy();
        }
        super.onDestroy();
    }


    public void finishPlayer() {
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }


    @Override
    public void onBackPressed() {
        finishPlayer();
        super.onBackPressed();
    }


    @Override
    public void onError(int what, int extra) {
        isErrorOccurred = true;
        restartPlayer();
    }

    public void setShowNavIcon(boolean show) {
        findViewById(R.id.app_video_finish).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setTitle(CharSequence title) {
        ((TextView) findViewById(R.id.app_video_title)).setText(title);
    }

}
