package com.umpee.app.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.umpee.app.R;

import static com.umpee.app.network.Network.makeToast;

public class UmpireThirdOptionMainViewActivity extends AppCompatActivity {

    VideoView vv1;
    Button btn_play_pause;
    Button btn_rewind;
    Button btn_fast_forward;
    Button btn_slow_motion;

    ProgressBar video_progress;

    Thread t;
    int stopPosition = 0;
    int interval_play_pause = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire_third_option_main_view);

        btn_play_pause = (Button)findViewById(R.id.play_pause);
        btn_rewind = (Button)findViewById(R.id.rewind);
        btn_fast_forward = (Button)findViewById(R.id.ff);
        btn_slow_motion = (Button)findViewById(R.id.slowmotion);

        video_progress = (ProgressBar)findViewById(R.id.video_progress);
        video_progress.setMax(100);

        vv1 = (VideoView)findViewById(R.id.vv1);
        String main_view_video = getIntent().getStringExtra("url_main_view_video");
        Uri uri = Uri.parse(main_view_video);

        //vv1.setMediaController(new MediaController(this));
        vv1.setVideoURI(uri);
        //vv1.requestFocus();
        vv1.start();
    }

    public void play_pause(View view){
        //t.interrupt();

        if (vv1.isPlaying()){
            stopPosition = vv1.getCurrentPosition();
            vv1.pause();
            btn_play_pause.setText("Play");
        }else{
            vv1.seekTo(stopPosition);
            vv1.start();
            btn_play_pause.setText("Pause");
        }

        int current = vv1.getCurrentPosition();
        int duration = vv1.getDuration();
        double value = current*(100/duration);
        int int_value = (int)value;
        video_progress.setProgress(int_value);
    }

    public void rewind(View view)
    {
        if (vv1 == null) {
            return;
        }

        //t.interrupt();

        vv1.seekTo(0);
        vv1.start();
        //btn_play_pause.setText("Play");

        int current = vv1.getCurrentPosition();
        int duration = vv1.getDuration();
        double value = current*(100/duration);
        int int_value = (int)value;
        video_progress.setProgress(int_value);
    }

    public void forward(View view)
    {
        if (vv1 == null) {
            return;
        }
        //t.interrupt();

        int pos = vv1.getCurrentPosition();
        pos += 1000; // milliseconds
        vv1.seekTo(pos);
        vv1.start();

        int current = vv1.getCurrentPosition();
        int duration = vv1.getDuration();
        double value = current*(100/duration);
        int int_value = (int)value;
        video_progress.setProgress(int_value);
    }

    public void slomo(View view){
        stopPosition = 0;
        vv1.pause();
        vv1.start();

        t=new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){
                    try {
                        Thread.sleep(interval_play_pause);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (vv1.isPlaying()){
                                    interval_play_pause = 500;
                                    stopPosition = vv1.getCurrentPosition();
                                    vv1.pause();
                                }else{
                                    interval_play_pause = 2000;
                                    vv1.seekTo(stopPosition);
                                    vv1.start();
                                }

                                int current = vv1.getCurrentPosition();
                                int duration = vv1.getDuration();
                                double value = current*(100/duration);
                                int int_value = (int)value;
                                video_progress.setProgress(int_value);
                            }
                        });
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                        t.interrupt();
                    }
                }
            }
        };

        t.start();
    }
}
