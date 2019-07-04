package com.umpee.app.activity;

//import android.media.MediaPlayer;
//import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.umpee.app.R;

public class UmpireThirdOptionMainViewActivity extends AppCompatActivity {

    VideoView vv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire_third_option_main_view);

        vv1 = (VideoView)findViewById(R.id.vv1);
        String main_view_video = getIntent().getStringExtra("url_main_view_video");
        Uri uri = Uri.parse(main_view_video);

        vv1.setMediaController(new MediaController(this));
        vv1.setVideoURI(uri);
        vv1.requestFocus();
        vv1.start();
    }

    /*public void slomo(MediaPlayer mp){
        PlaybackParams myPlayBackParams = new PlaybackParams();
        myPlayBackParams.setSpeed(0.5f);
        mp.setPlaybackParams(myPlayBackParams);
        vv1.start();
    }*/
}
