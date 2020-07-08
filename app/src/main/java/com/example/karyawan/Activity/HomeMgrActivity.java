package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.karyawan.R;

public class HomeMgrActivity extends AppCompatActivity {

    private ImageView ivapprove;

    public SharedPreferences pref, prf;

    String id_krw;
    String divisi;

    final static String TAG = HomeMgrActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_mgr);

        ivapprove = findViewById(R.id.ivapprove);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id kar "+ id_krw);

        pref = getSharedPreferences("Divisi", MODE_PRIVATE);
        divisi = pref.getString("divisi", null);
        Log.e(TAG, "divisi "+ divisi);

        ivapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ApprovalActivity.class);

                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                id_krw =pref.getString("id_krw", null);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_krw", id_krw);
                editor.commit();

                pref = getSharedPreferences("Divisi", MODE_PRIVATE);
                divisi = pref.getString("divisi", null);
                SharedPreferences.Editor editor1 = pref.edit();
                editor.putString("divisi", divisi);
                editor1.commit();

                startActivity(intent);
            }
        });
    }
}