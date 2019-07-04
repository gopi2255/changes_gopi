package com.umpee.app.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.umpee.app.R;

public class UmpireThirdOptionLegViewActivity extends AppCompatActivity {

    VideoView vv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire_third_option_leg_view);

        vv2 = (VideoView)findViewById(R.id.vv2);
        String leg_view_video = getIntent().getStringExtra("url_leg_view_video");
        Uri uri = Uri.parse(leg_view_video);

        vv2.setMediaController(new MediaController(this));
        vv2.setVideoURI(uri);
        vv2.requestFocus();
        vv2.start();
    }
}
