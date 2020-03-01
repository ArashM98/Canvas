package com.ArashTorDev.tablo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.VideoView;

public class Activity_Intro extends AppCompatActivity {

    VideoView videoView;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Intent intent = new Intent(Activity_Intro.this, Activity_Main.class);
        startActivity(intent);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // in this runnable we have a video or gif that runs for introducing or showing app brands
            }
        };

        // playing a short video for apps introduction
        videoView = findViewById(R.id.Video_view);
        // copy a video for intro and set the address below
//        videoView.setVideoPath("/sdcard/Telegram/Telegram Video/4_6019421825262421151.mp4");
//        videoView.start();

        // after 4 seconds it will automatically go to main activity
        handler.postDelayed(runnable, 100);
    }
}
