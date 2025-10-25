package com.example.cafeku.Supabase;

import static java.time.LocalTime.now;
import java.time.Instant;
import java.util.Locale;
import okhttp3.*;

public class SupabaseService {

    private OkHttpClient client = new OkHttpClient();
    private MediaType JSON = MediaType.get("application/json");

    public void insertRating(int userId, int ratingInt,String name, Callback callback) {

        String url = SupabaseConfig.SUPABASE_URL + "/rest/v1/ratings";

        String json = String.format(Locale.US,
                "{ \"user_id\": %d, \"rating\": %d, \"username\": \"%s\" }",
                userId, ratingInt, name
                // %d = khusus untuk integer
                //%f = khusus untuk float/double
                //%s = untuk string
        );

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("apikey", SupabaseConfig.SUPABASE_KEY)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        client.newCall(request).enqueue(callback);
    }

    // GET AVERAGE RATING ✅
    // SupabaseService.java
    public void getAverageRating(Callback callback) {

        String url = SupabaseConfig.SUPABASE_URL + "/rest/v1/rpc/get_average_rating";


        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("", JSON))
                .addHeader("apikey", SupabaseConfig.SUPABASE_KEY)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                .build();


        client.newCall(request).enqueue(callback);
    }

    // UPDATE rating by user_id
    public void updateRating(int userId, float newRating, Callback callback) {

        String url = SupabaseConfig.SUPABASE_URL + "/rest/v1/ratings?user_id=eq." + userId;

        String json = "{ \"rating\": " + newRating + " }";
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .addHeader("apikey", SupabaseConfig.SUPABASE_KEY)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);
    }

    // DELETE Rating by user_id
    public void deleteRating(int userId, Callback callback) {

        String url = SupabaseConfig.SUPABASE_URL + "/rest/v1/ratings?user_id=eq." + userId;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("apikey", SupabaseConfig.SUPABASE_KEY)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                .build();

        client.newCall(request).enqueue(callback);
    }
    public  void checkUserRatingExists(int userId, Callback callback) {
        // Parameter:
        // 1. ?user_id=eq.{userId}  -> Filter berdasarkan user_id
        // 2. &select=id           -> Hanya ambil kolom 'id' (lebih ringan/efisien)
        // 3. &limit=1             -> Batasi hanya 1 record saja yang diambil

        String url = SupabaseConfig.SUPABASE_URL + "/rest/v1/ratings"
                + "?user_id=eq." + userId
                + "&select=id"
                + "&limit=1";

        Request request = new Request.Builder()
                .url(url)
                .get() // Metode HTTP GET
                .addHeader("apikey", SupabaseConfig.SUPABASE_KEY)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
