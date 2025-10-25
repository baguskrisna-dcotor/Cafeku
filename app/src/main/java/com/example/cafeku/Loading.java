package com.example.cafeku;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

public class Loading extends AppCompatActivity {

    public static volatile boolean targetReady = false;
    private Intent targetIntent;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private VideoView videoView;

    private final BroadcastReceiver readyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("LoadingBridge", "✅ Received READY broadcast");
            targetReady = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetReady = false;
        setContentView(R.layout.loading);

        videoView = findViewById(R.id.loading_video);

        // Daftarkan receiver lokal
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(readyReceiver, new IntentFilter("TARGET_READY"));

        // Ambil nama Activity tujuan
        String targetClassName = getIntent().getStringExtra("targetClass");
        if (targetClassName != null) {
            try {
                Class<?> targetClass = Class.forName(targetClassName);
                targetIntent = new Intent(this, targetClass);
                targetIntent.putExtras(getIntent().getExtras());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Putar video loading looping
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.loading_video);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });

        // Mulai memantau halaman siap
        startCheckingTargetReady();
    }

    private void startCheckingTargetReady() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (targetReady) {
                    openTargetActivity();
                } else {
                    handler.postDelayed(this, 300);
                }
            }
        }, 300);
    }

    private void openTargetActivity() {
        if (targetIntent != null) {
            targetIntent.putExtra("fromLoading", true);
            startActivity(targetIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(readyReceiver);
        handler.removeCallbacksAndMessages(null);
        if (videoView != null) videoView.stopPlayback();
    }
}
