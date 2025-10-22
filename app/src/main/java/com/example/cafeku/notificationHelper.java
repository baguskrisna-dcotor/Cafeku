package com.example.cafeku;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cafeku.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.util.ArrayList;


public class notificationHelper {

    public static ArrayList<Integer> Id = new ArrayList<>();
    public static ArrayList<String> NamaProduk = new ArrayList<>();
    public static ArrayList<Integer> HargaProduk = new ArrayList<>();
    public static ArrayList<String> DeskripsiProduk = new ArrayList<>();
    public static ArrayList<String> GambarProduk = new ArrayList<>();
    public static ArrayList<Integer> PointProduk = new ArrayList<>();


    // ini chanel yang digunakan notifikasi (penting)
    public static final String CHANNEL_ID = "cafeku_channel";

    public notificationHelper(Context context){
        JSONgetList(context, Id, NamaProduk, DeskripsiProduk, HargaProduk, GambarProduk, PointProduk);
    }

    public static void showNotification(Context context, String message, Class<?> contextDest) {
        showNotification(context, message, contextDest, -1);
    }


    // Menampilkan notifikasi ke pengguna
    public static void showNotification(Context context, String message, Class<?> contextDest, int idIntent) {

        //mengecek apakah android versi Oreo atau lebih baru
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    notificationHelper.CHANNEL_ID,
                    "Cafeku Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            //Layanan sistem untuk mengatur notifikasi
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            //Mendaftarkan channel ke android
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        //intent yang akan dijalankan ketika notifikasi diklik
        Intent intent = new Intent(context, contextDest);
        if(idIntent != -1){
            int index = idIntent - 1;
            if (index != -1) {
                intent.putExtra("name", NamaProduk.get(index));
                intent.putExtra("deskripsi", DeskripsiProduk.get(index));
                intent.putExtra("harga", HargaProduk.get(index));
                intent.putExtra("gambar", GambarProduk.get(index) + ".png");
                intent.putExtra("point", PointProduk.get(index));
            }

        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                idIntent != -1 ? idIntent : (int) System.currentTimeMillis(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );


        //Set konyen yang akan ditampilkan dalam notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationHelper.CHANNEL_ID)
                .setSmallIcon(R.drawable.cafeku)
                .setContentTitle("CAFEKU")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //mengecek apakah android versi Tiramisu atau lebih baru
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    private void JSONgetList(Context context, ArrayList<Integer> id , ArrayList<String> NamaProduk, ArrayList<String> DeskripsiProduk, ArrayList<Integer> HargaProduk, ArrayList<String> GambarProduk, ArrayList<Integer> Point) {

        try {

            AssetManager am = context.getAssets();
            String[] files = am.list("");
            for (String name : files) {
                Log.d("Assets", "File: " + name);
            }

            InputStream is = context.getAssets().open("dataFullProduk.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String file = new String(buffer, "UTF-8");
            JSONArray JSONarray = new JSONArray(file);

            for(int i = 0; i < JSONarray.length(); i++){
                JSONObject object = JSONarray.getJSONObject(i);
                id.add(object.getInt("id"));
                NamaProduk.add(object.getString("nama"));
                DeskripsiProduk.add(object.getString("deskripsi"));
                HargaProduk.add(object.getInt("harga"));
                GambarProduk.add(object.getString("gambar"));
                Point.add(object.getInt("point"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
}
    public static int findID(int id){
        for(int i = 0; i < Id.size(); i++){
            if(Id.get(i) == id) {
                return i;
            }
}
        return -1;
    }
}
