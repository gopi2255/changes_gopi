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

public class UmpireThirdOptionMainViewActivity extends AppCompatActivity {

    VideoView vv1;
    Button btn_slomo12;
    Button btn_slomo14;
    Button btn_slomo34;
    Button btn_normal;

    private MediaPlayer mpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire_third_option_main_view);

        btn_slomo12 = (Button)findViewById(R.id.slowmo12);
        btn_slomo14 = (Button)findViewById(R.id.slowmo14);
        btn_slomo34 = (Button)findViewById(R.id.slowmo34);
        btn_normal = (Button)findViewById(R.id.normal);

        vv1 = (VideoView)findViewById(R.id.vv1);
        String main_view_video = getIntent().getStringExtra("url_main_view_video");
        Uri uri = Uri.parse(main_view_video);
        vv1.setMediaController(new MediaController(this));
        vv1.setVideoURI(uri);

        vv1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //works only from api 23
                mpl = mp;
                PlaybackParams myPlayBackParams = new PlaybackParams();
                myPlayBackParams.setSpeed(1.0f);
                mp.setPlaybackParams(myPlayBackParams);
                vv1.requestFocus();
                vv1.start();
            }
        });
    }

    public void slow_motion_14(View view){
        mpl.setPlaybackParams(mpl.getPlaybackParams().setSpeed(0.25f));
        vv1.start();
    }

    public void slow_motion_12(View view){
        mpl.setPlaybackParams(mpl.getPlaybackParams().setSpeed(0.5f));
        vv1.start();
    }

    public void slow_motion_34(View view){
        mpl.setPlaybackParams(mpl.getPlaybackParams().setSpeed(0.75f));
        vv1.start();
    }

    public void normal_motion(View view){
        mpl.setPlaybackParams(mpl.getPlaybackParams().setSpeed(1.0f));
        vv1.start();
    }
}
