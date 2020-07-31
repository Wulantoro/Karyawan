package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.karyawan.Adapter.MenuAdapter;
import com.example.karyawan.Interface.RecyclerViewClick;
import com.example.karyawan.Model.ApproveMenu;
import com.example.karyawan.R;

import java.util.ArrayList;
import java.util.List;

public class ApprovalHrdActivity extends AppCompatActivity {

    public SharedPreferences pref, prf;

    String id_krw;
    String divisi;

    private String TAG = ApprovalActivity.class.getSimpleName();

    List<ApproveMenu> list =new ArrayList<>();

    private RecyclerView rvMenu;
    private MenuAdapter menuAdapter;
    private LinearLayout parentLayout;

    private ApproveMenu menu;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_hrd);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id kar " + id_krw);

//        pref = getSharedPreferences("Divisi", MODE_PRIVATE);
//        divisi = pref.getString("divisi", null);
//        Log.e(TAG, "divisi " + divisi);

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
        intent = new Intent(getApplicationContext(), ApprovalCutiHrdActivity.class);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw =pref.getString("id_krw", null);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id_krw", id_krw);
        editor.commit();

//        pref = getSharedPreferences("Divisi", MODE_PRIVATE);
//        divisi = pref.getString("divisi", null);
//        SharedPreferences.Editor editor1 = pref.edit();
//        editor.putString("divisi", divisi);
//        editor1.commit();

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