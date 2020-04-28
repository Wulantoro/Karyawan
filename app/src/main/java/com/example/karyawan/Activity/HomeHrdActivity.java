package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.karyawan.R;

public class HomeHrdActivity extends AppCompatActivity {

    private ImageView ivkaryawan;
    private ImageView ivprofil;
    private ImageView ivarsip;

    public SharedPreferences pref, prf;

    String id_krw;

    private static final String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_hrd);

        ivkaryawan = findViewById(R.id.ivkaryawan);
        ivprofil = findViewById(R.id.ivprofil);
        ivarsip = findViewById(R.id.ivarsip);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);

        ivkaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KaryawanActivity.class);
                startActivity(intent);
            }
        });

        ivprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                id_krw =pref.getString("id_krw", null);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_krw", id_krw);
                editor.commit();
                startActivity(intent);
            }
        });

        ivarsip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AttendanceActivity.class);
                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                id_krw =pref.getString("id_krw", null);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("id_krw");
                editor.commit();

//                SharedPreferences settings = getApplicationContext().getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
//                settings.edit().remove("KeyName").commit();

                startActivity(intent);
            }
        });
    }
}
