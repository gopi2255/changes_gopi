package com.umpee.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.umpee.app.R;

public class UmpireThirdOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire_third_options);
    }

    public void goto_animation(View view){
        Intent animation = new Intent(this, UmpireThirdOptionAnimationActivity.class);
        startActivity(animation);
    }

    public void goto_main_view(View view){
        Intent main_view = new Intent(this, UmpireThirdOptionMainViewActivity.class);
        main_view.putExtra("url_main_view_video", "https://wilawaranayra.s3-sa-east-1.amazonaws.com/gopi/main1.5.mp4");
        startActivity(main_view);
    }

    public void goto_leg_view(View view){
        Intent leg_view = new Intent(this, UmpireThirdOptionLegViewActivity.class);
        leg_view.putExtra("url_leg_view_video", "https://wilawaranayra.s3-sa-east-1.amazonaws.com/gopi/leg1.5B.mp4");
        startActivity(leg_view);
    }
}
