package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.karyawan.R;

public class HomeActivity extends AppCompatActivity {

    private ImageView ivuser;
    private ImageView ivabsent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ivuser = findViewById(R.id.ivuser);
        ivabsent = findViewById(R.id.ivabsent);

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
                Intent intent = new Intent(getApplicationContext(), AttendanceActivity.class);
                startActivity(intent);
            }
        });
    }

}
