package com.example.cafeku;

import static com.example.cafeku.MainActivity.PERMISSION_REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class FirstOpen extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 101;
    ArrayList<String> textNotif = new ArrayList<>();
    ArrayList<String> destNotif = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_open);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cekIzinNotifikasi();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
    private void cekIzinNotifikasi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            // Pertama kali, minta izin dulu
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Izin sudah ada → tampilkan notif random
            JSONgetList(textNotif, destNotif);
            randomMessage(textNotif.toArray(new String[0]), destNotif.toArray(new String[0]));
        }
    }

    private void JSONgetList(ArrayList<String> textList, ArrayList<String> destList) {
        try {
            InputStream is = getAssets().open("textNotif.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String file = new String(buffer, "UTF-8");
            JSONArray NotifJSONarray = new JSONArray(file);

            for(int i = 0; i < NotifJSONarray.length(); i++){
                JSONObject object = NotifJSONarray.getJSONObject(i);
                textList.add(object.getString("text"));
                destList.add(object.getString("dest"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void randomMessage(String[] textList, String[] destList){
        if (textList == null || destList == null || textList.length == 0 || destList.length == 0) {
            return;
        }
        Random random = new Random();
        int randNum = random.nextInt(textList.length);
        String text = textList[randNum];
        String dest = destList[randNum];

        try{
            Class<?> destination = Class.forName(dest);
            notificationHelper.showNotification(this, text, destination);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}