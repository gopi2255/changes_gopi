package com.umpee.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umpee.app.R;
import com.umpee.app.cache.Cache;
import com.umpee.app.custom.CustomActivity;
import com.umpee.app.firebase.FirebaseUtils;
import com.umpee.app.model.ModelVideo;
import com.umpee.app.server.ServiceReceiver;
import com.umpee.app.utils.DateTimeUtils;
import com.umpee.app.utils.DialogUtils;

public class UmpireActivity extends CustomActivity implements View.OnClickListener {

    protected ImageView imageThumb;
    protected ImageButton buttonPlay;
    protected TextView textStart;
    protected TextView textEnd;
    protected TextView textDuration;
    protected TextView textNoVideo;
    protected Button buttonRecord;
    protected LinearLayout layoutContent;
    protected Button buttonLogout;
    protected Button buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_umpire);
        setupActionbar("Umpire Home", false);
        initView();
        ServiceReceiver.startService(getApplicationContext());
        FirebaseUtils.registerTopic("umpire");
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderView();
    }

    private void renderView() {
        ModelVideo video = Cache.getVideo();
        if (video.checkVideo()) {
            layoutContent.setVisibility(View.VISIBLE);
            textNoVideo.setVisibility(View.GONE);
            imageThumb.setImageBitmap(video.getThumb());
            textDuration.setText(DateTimeUtils.getTimeString(video.duration));
            textStart.setText(video.startTime);
            textEnd.setText(video.endTime);
        } else {
            layoutContent.setVisibility(View.GONE);
            textNoVideo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonPlay) {
            ModelVideo video = Cache.getVideo();
            if (video.checkVideo()) {
                startActivity(PlayerActivity.newIntent(this, "umpee", video.url));
            } else {
                makeToast("No video file recorded yet");
            }
        } else if (view.getId() == R.id.buttonRecord) {
            DialogUtils.showOkayCancelDialog(this, "Confirm", "When you start new record, the saved video file will be replaced with new file.\nWould you like to start new recording now?", "Yes", "Later", new DialogUtils.OnOkayCancelListener() {
                @Override
                public void onOkay() {
                    startActivity(CameraActivity.class);
                }

                @Override
                public void onCancel() {

                }
            });
        } else if (view.getId() == R.id.buttonLogout) {
            getUser().logout();
            restartActivity(SelectRoleActivity.class);
        } else if (view.getId() == R.id.buttonFinish) {
//            ServiceReceiver.stopService(getApplicationContext());
            finish();
        }
    }

    private void initView() {
        imageThumb = (ImageView) findViewById(R.id.imageThumb);
        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(UmpireActivity.this);
        textStart = (TextView) findViewById(R.id.textStart);
        textEnd = (TextView) findViewById(R.id.textEnd);
        textDuration = (TextView) findViewById(R.id.textDuration);
        textNoVideo = (TextView) findViewById(R.id.textNoVideo);
        buttonRecord = (Button) findViewById(R.id.buttonRecord);
        buttonRecord.setOnClickListener(UmpireActivity.this);
        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(UmpireActivity.this);
        buttonFinish = (Button) findViewById(R.id.buttonFinish);
        buttonFinish.setOnClickListener(UmpireActivity.this);
    }
}
