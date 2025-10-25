package com.example.cafeku;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

public abstract class BaseActivity extends AppCompatActivity {

    protected void goToWithLoading(Intent intent) {
        Intent loadingIntent = new Intent(this, Loading.class);
        loadingIntent.putExtra("targetClass", intent.getComponent().getClassName());
        if (intent.getExtras() != null)
            loadingIntent.putExtras(intent.getExtras());
        startActivity(loadingIntent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    protected void markReady() {
        Log.d("LoadingBridge", "📤 Sending READY broadcast");
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(new Intent("TARGET_READY"));
    }
}
