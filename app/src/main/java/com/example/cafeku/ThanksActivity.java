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



public class ThanksActivity extends AppCompatActivity {

    ImageView imgdecor1,imgdecor2;
    VideoView vtSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        vtSuccess = findViewById(R.id.vtSuccess);
        imgdecor1 = findViewById(R.id.imgdecor1);
        imgdecor2 = findViewById(R.id.imgdecor2);
        TextView txtThanks = findViewById(R.id.txtThanks);
        TextView txtMessage = findViewById(R.id.txtMessage);


        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.thanksblack;
        Uri uri = Uri.parse(videoPath);
        vtSuccess.setVideoURI(uri);
        vtSuccess.requestFocus();

        vtSuccess.setOnPreparedListener(mp -> {
            vtSuccess.setBackground(null);
            mp.setLooping(false);
            vtSuccess.start();
        });


        Animation leftAnim = AnimationUtils.loadAnimation(this, R.anim.fadedownleft);
        Animation rightAnim = AnimationUtils.loadAnimation(this, R.anim.fadeupright);

        imgdecor1.setVisibility(ImageView.VISIBLE);
        imgdecor2.setVisibility(ImageView.VISIBLE);

        imgdecor2.startAnimation(leftAnim);
        imgdecor1.startAnimation(rightAnim);

        // Fade-in untuk teks
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

        // Simulasikan loading 3 detik lalu ke Thanks screen
        new Handler().postDelayed(() -> {
            startActivity(new Intent(ThanksActivity.this, Profile.class));
            finish();
        }, 5000);
    }
}
