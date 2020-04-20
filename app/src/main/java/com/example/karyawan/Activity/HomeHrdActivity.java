package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.karyawan.R;

public class HomeHrdActivity extends AppCompatActivity {

    private ImageView ivkaryawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_hrd);

        ivkaryawan = findViewById(R.id.ivkaryawan);

        ivkaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KaryawanActivity.class);
                startActivity(intent);
            }
        });
    }
}
