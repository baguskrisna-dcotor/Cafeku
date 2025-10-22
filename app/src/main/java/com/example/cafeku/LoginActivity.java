package com.example.cafeku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cafeku.DAO.ImgDao;
import com.example.cafeku.DAO.UserDao;
import com.example.cafeku.database.ImgDatabase;
import com.example.cafeku.database.UserDatabase;
import com.example.cafeku.model.Img;
import com.example.cafeku.model.User;

import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends Activity {
    private EditText etusername,etpw;
    private Button btnlogin;

    private LinearLayout guest;




    private RadioGroup options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etusername = findViewById(R.id.et_username);
        etpw = findViewById(R.id.et_password);
        btnlogin = findViewById(R.id.btn_login);
        options = findViewById(R.id.radioGroupOptions);
        guest = findViewById(R.id.btn_guest_login);

        AtomicReference<Boolean> gender = new AtomicReference<>();

        //memberikan fugnsi listener agar sistem dapat mengambil data dari opsi mana yang user pilih
        options.setOnCheckedChangeListener((group,radioid)->{

            //Kita sudah punya variabel boolean untuk menyimpan gender user
            if(radioid == R.id.Female){
                //jika yang dipilih user adalah radio button dengan id female maka gender aka bernilai false yang berarti Perempuan
                //begitu juga sebaliknya
                gender.set(false);
            } else if (radioid == R.id.Male) {
                gender.set(true);
            }else {
                Toast.makeText(this,"gender?",Toast.LENGTH_SHORT).show();
            }
        });

        btnlogin.setOnClickListener(v ->{
            //ketika user login semua data yang user berikan akan masuk ke variabel lokal di dalam listener ini
            String username = etusername.getText().toString().trim();
            String password = etpw.getText().toString().trim();
            Boolean sex = gender.get().booleanValue();

            //mengecek apkah user sudah mengisi atau belum
            if(username.isEmpty()|| password.isEmpty() ){
                Toast.makeText(this,"Isi dulu",Toast.LENGTH_LONG).show();
                return ;
            }
            UserDatabase db = UserDatabase.getInstance(this);
            UserDao userdao = db.userDao();
            User existing = userdao.getUser();


            if (existing != null && existing.username.equals(username)) {
                Toast.makeText(this, "Namamu sudah ada", Toast.LENGTH_LONG).show();
                return;
            }

            ImgDatabase ib = ImgDatabase.getInstance(this);
            ImgDao dao = ib.imgDao();
            dao.deleteAll();

            User newuser = new User(username,password,sex);
            userdao.insertUser(newuser);
            Toast.makeText(this,"Welcome to CAFEKU",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, CheckActivity.class);
            Intent i =new Intent(LoginActivity.this, Profile.class);
            i.putExtra("username", username);
            i.putExtra("gender",sex);
            startActivity(intent);
            finish();
        });

        guest.setOnClickListener(V->{
            Intent i = new Intent(LoginActivity.this, Profile.class);
            Toast.makeText(this,"Lu pakai guest ya ",Toast.LENGTH_SHORT).show();
            startActivity(i);
        });


}}
