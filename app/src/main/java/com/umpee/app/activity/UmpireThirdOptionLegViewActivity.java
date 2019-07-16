package com.umpee.app.activity;

import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.umpee.app.R;

public class UmpireThirdOptionLegViewActivity extends AppCompatActivity {

    VideoView vv2;
    Button btn_slomo12;
    Button btn_slomo14;
    Button btn_slomo34;
    Button btn_normal;

    private MediaPlayer mpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire_third_option_leg_view);

        btn_slomo12 = (Button)findViewById(R.id.slowmo12_leg);
        btn_slomo14 = (Button)findViewById(R.id.slowmo14_leg);
        btn_slomo34 = (Button)findViewById(R.id.slowmo34_leg);
        btn_normal = (Button)findViewById(R.id.normal_leg);

        vv2 = (VideoView)findViewById(R.id.vv2);
        String leg_view_video = getIntent().getStringExtra("url_leg_view_video");
        Uri uri = Uri.parse(leg_view_video);
        vv2.setMediaController(new MediaController(this));
        vv2.setVideoURI(uri);

        vv2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //works only from api 23
                mpl = mp;
                PlaybackParams myPlayBackParams = new PlaybackParams();
                myPlayBackParams.setSpeed(1.0f);
                mp.setPlaybackParams(myPlayBackParams);
                vv2.requestFocus();
                vv2.start();
            }
        });
    }

    public void slow_motion_14_leg(View view){
        mpl.setPlaybackParams(mpl.getPlaybackParams().setSpeed(0.25f));
        vv2.start();
    }

    public void slow_motion_12_leg(View view){
        mpl.setPlaybackParams(mpl.getPlaybackParams().setSpeed(0.5f));
        vv2.start();
    }

    public void slow_motion_34_leg(View view){
        mpl.setPlaybackParams(mpl.getPlaybackParams().setSpeed(0.75f));
        vv2.start();
    }

    public void normal_motion_leg(View view){
        mpl.setPlaybackParams(mpl.getPlaybackParams().setSpeed(1.0f));
        vv2.start();
    }
}
