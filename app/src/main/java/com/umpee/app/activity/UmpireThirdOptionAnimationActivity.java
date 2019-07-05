package com.umpee.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.umpee.app.R;

public class UmpireThirdOptionAnimationActivity extends AppCompatActivity {

    WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire_third_option_animation);

        wv1 = (WebView)findViewById(R.id.wv1);
        wv1.setWebViewClient(new WebViewClient());

        //Enabling javascript settings to show 3d scene
        WebSettings webSettings = wv1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        /*webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);*/

        wv1.loadUrl("http://ec2-52-87-46-240.compute-1.amazonaws.com/simulation.html");
    }
}
