package com.example.cafeku;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;



public class CheckActivity extends AppCompatActivity {

    VideoView vtSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        vtSuccess = findViewById(R.id.vtSuccess);
        TextView txtThanks = findViewById(R.id.txtThanks);
        TextView txtMessage = findViewById(R.id.txtMessage);


        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.checkanimation;
        Uri uri = Uri.parse(videoPath);
        vtSuccess.setVideoURI(uri);
        vtSuccess.requestFocus();


        vtSuccess.setOnPreparedListener(mp -> {
            vtSuccess.setBackground(null);
            mp.setLooping(false);
            vtSuccess.start();
        });

        Animation a = AnimationUtils.loadAnimation(this,R.anim.updown);
        vtSuccess.startAnimation(a);
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(1200);
        fadeIn.setStartOffset(600);
        txtThanks.startAnimation(fadeIn);
        txtThanks.setAlpha(1f);


        AlphaAnimation fadeIn2 = new AlphaAnimation(0f, 1f);
        fadeIn2.setDuration(1400);
        fadeIn2.setStartOffset(1200);
        txtMessage.startAnimation(fadeIn2);
        txtMessage.setAlpha(1f);


        // Simulasikan loading 5 detik lalu ke Thanks screen
        new Handler().postDelayed(() -> {
            notificationHelper.showNotification(this,  "CAFEKU Login .",Profile.class);
            notificationHelper.showNotification(this,  "Terimakasih sudah login yaaa 😊",Profile.class);
            startActivity(new Intent(CheckActivity.this, Profile.class));
            finish();
        }, 5000);
    }
}
