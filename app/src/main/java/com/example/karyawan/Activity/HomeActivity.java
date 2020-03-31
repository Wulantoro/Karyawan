package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.karyawan.R;

public class HomeActivity extends AppCompatActivity {

    private ImageView ivuser;
    private ImageView ivabsent;
    private ImageView ivattendance;
    public SharedPreferences pref, prf;

    String id_krw;

    private static final String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ivuser = findViewById(R.id.ivuser);
        ivabsent = findViewById(R.id.ivabsent);
        ivattendance = findViewById(R.id.ivattendance);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);



        ivuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(intent);
            }
        });

        ivabsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AttendanceActivity.class);
                Intent intent = new Intent(getApplicationContext(), AbsenActivity.class);
                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                id_krw =pref.getString("id_krw", null);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_krw", id_krw);
                editor.commit();
                startActivity(intent);
            }
        });

        ivattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AttendanceActivity.class);
                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                id_krw =pref.getString("id_krw", null);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_krw", id_krw);
                editor.commit();
                startActivity(intent);
            }
        });
    }

}
