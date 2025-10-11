package com.example.cafeku;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private TextView tvProfile;
    private ImageView btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        tvProfile = findViewById(R.id.nameUser);
        btnMore = findViewById(R.id.settingbutton);

        // klik tombol gear
        btnMore.setOnClickListener(v -> showPopupMenu(v));
    }

    private void showPopupMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenuInflater().inflate(R.menu.profile_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_edit_profile) {
                showEditProfileDialog();
                return true;
            } else if (id == R.id.menu_my_account) {
                Toast.makeText(this, "My Account clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_logout) {
                Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false; // cancel tidak perlu aksi
        });

        popup.show();
    }

    private void showEditProfileDialog() {
        // Inflate layout custom
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_edit_profile, null);

        EditText etName = view.findViewById(R.id.et_name);
        Button btnSave = view.findViewById(R.id.btn_save);
        ImageView btnClose = view.findViewById(R.id.btn_close);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false) // agar tidak bisa dismiss sembarangan
                .create();

        // klik tombol Save
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            // update profile di halaman utama
            tvProfile.setText( name );

            Toast.makeText(this, "Data tersimpan!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        // klik tombol X (close)
        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
