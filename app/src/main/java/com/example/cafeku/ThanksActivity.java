package com.example.cafeku;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class ThanksActivity extends AppCompatActivity {

    ImageView imgdecor1,imgdecor2,imgSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        imgSuccess = findViewById(R.id.imgSuccess);
        imgdecor1 = findViewById(R.id.imgdecor1);
        imgdecor2 = findViewById(R.id.imgdecor2);
        TextView txtThanks = findViewById(R.id.txtThanks);
        TextView txtMessage = findViewById(R.id.txtMessage);

        Animation leftAnim = AnimationUtils.loadAnimation(this, R.anim.fadedownleft);
        Animation rightAnim = AnimationUtils.loadAnimation(this, R.anim.fadeupright);

        imgdecor1.setVisibility(ImageView.VISIBLE);
        imgdecor2.setVisibility(ImageView.VISIBLE);

        imgdecor2.startAnimation(leftAnim);
        imgdecor1.startAnimation(rightAnim);


        ScaleAnimation scaleAnim = new ScaleAnimation(
                0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(800);
        scaleAnim.setFillAfter(true);
        imgSuccess.startAnimation(scaleAnim);

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
            startActivity(new Intent(ThanksActivity.this, MainActivity.class));
            finish();
        }, 4000);
    }
}
