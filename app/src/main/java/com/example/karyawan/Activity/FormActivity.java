package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog;
import com.example.karyawan.Adapter.MenuAdapter;
import com.example.karyawan.Interface.RecyclerViewClick;
import com.example.karyawan.Model.ApproveMenu;
import com.example.karyawan.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {

    private Spinner spJenis;
    private ImageView imgTglmulai;
    private EditText etTglmulai;
    private EditText etTglselesai;
    private ImageView ivTglselesai;
    private TextView tvlama;
    private Button btnSimpan;

    List<ApproveMenu> list =new ArrayList<>();

    private RecyclerView rvMenu;
    private MenuAdapter menuAdapter;
    private LinearLayout parentLayout;

    private ApproveMenu menu;
    private Intent intent;
    public SharedPreferences pref, prf;

    String id_krw;


    private String[] jenis = {
            "--Pilih--",
            "Cuti",
            "Sakit",
            "Izin"
    };
    private static final String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);


        rvMenu = findViewById(R.id.rvMenu);
        parentLayout = findViewById(R.id.parentLayout);

        approveMenu();


    }


    private void approveMenu() {
        //0
        menu = new ApproveMenu(R.string.cuti);
        list.add(menu);
        //1
        menu = new ApproveMenu(R.string.izin);
        list.add(menu);
        //2
        menu = new ApproveMenu(R.string.sakit);
        list.add(menu);

        menuAdapter = new MenuAdapter(this, list, new RecyclerViewClick() {
            @Override
            public void onItemClick(View v, int position) {
                if (position == 0) {
                    intentToCuti();
                } else if (position == 1) {
                    intenToIzin();
                }else if (position == 2) {
                    intenToSakit();
                }
            }
        });

        rvMenu.setHasFixedSize(true);
        rvMenu.setAdapter(menuAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMenu.setLayoutManager(linearLayoutManager);
        rvMenu.setItemAnimator(new DefaultItemAnimator());

    }

    private void intentToCuti() {
        intent = new Intent(getApplicationContext(), CutiActivity.class);
        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw =pref.getString("id_krw", null);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id_krw", id_krw);
        editor.commit();
        startActivity(intent);
    }

    private void intenToIzin() {
        intent = new Intent(getApplicationContext(), IzinActivity.class);
        startActivity(intent);
    }

    private void intenToSakit() {
        intent = new Intent(getApplicationContext(), SakitActivity.class);
        startActivity(intent);
    }




}
