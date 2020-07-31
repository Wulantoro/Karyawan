package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.karyawan.Adapter.CutiAdapter;
import com.example.karyawan.Model.Cuti;
import com.example.karyawan.Model.Karyawan;
import com.example.karyawan.R;
import com.example.karyawan.Utils.GlobalVars;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.karyawan.Utils.GlobalVars.BASE_IP;

public class CutiActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView rvCuti;
    private CutiAdapter cutiAdapter;
    private Cuti cuti;
    private Gson gson;
    public SharedPreferences pref, prf;

    String id_krw;
    private static String TAG = CutiActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuti);

        fab = findViewById(R.id.fab);
        rvCuti = findViewById(R.id.rvCuti);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);

        List<Cuti> result = new ArrayList<>();
        result.add(cuti);

        gson = new Gson();
        cutiAdapter = new CutiAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CutiActivity.this);
        rvCuti.setLayoutManager(layoutManager);
        loadCuti(id_krw);
        rvCuti.setAdapter(cutiAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FormCutiActivity.class);
                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                id_krw =pref.getString("id_krw", null);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_krw", id_krw);
                editor.commit();
                startActivity(intent);
            }
        });
    }

    private void loadCuti(String id_krw) {
        if (cutiAdapter != null) {
            cutiAdapter.clearAll();

            Log.e(TAG, "ip = " + GlobalVars.BASE_IP+"cuti?id_kar=" + id_krw);
            AndroidNetworking.get(GlobalVars.BASE_IP + "cuti?id_kar=" + id_krw )
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<Cuti> result = new ArrayList<>();
                            try {
                                Log.e(TAG, "tampil cuti = " + response.toString(1));

                                if (result != null)
                                    result.clear();

                                String message = response.getString("message");
                                if (message.equalsIgnoreCase("Data cuti ditemukan")) {
                                    String records = response.getString("data");
                                    JSONArray dataArr = new JSONArray(records);

                                    if (dataArr.length() > 0) {
                                        for (int i = 0; i < dataArr.length(); i++) {
                                            Cuti cuti = gson.fromJson(dataArr.getJSONObject(i).toString(), Cuti.class);
                                            result.add(cuti);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            cutiAdapter.addAll(result);
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }
    }

    public void onBackPressed() {
        super.onBackPressed();

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id_krw", id_krw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.get(BASE_IP + "karyawan?id_krw=" + id_krw)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.e(TAG, "response = " + response.toString(1));

                            String message = response.getString("message");

                            if (message.equals("Karyawan Ditemukan")) {
                                String records = response.getString("data");
                                JSONObject jsonObject = new JSONObject(records);

                                Karyawan karyawan = gson.fromJson(jsonObject.toString(), Karyawan.class);

                                if (karyawan.getDivisi().equalsIgnoreCase("HRD")) {
                                    Intent intent = new Intent(getApplicationContext(), HomeHrdActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    finish();
                                } else if (karyawan.getLevel().equalsIgnoreCase("1")){
                                    Intent intent = new Intent(getApplicationContext(), HomeMgrActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    finish();
                                } else if (karyawan.getLevel().equalsIgnoreCase("2") || karyawan.getLevel().equalsIgnoreCase("3")) {
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    finish();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
