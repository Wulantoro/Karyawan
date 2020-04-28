package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.karyawan.Model.Absent;
import com.example.karyawan.Adapter.AbsentAdapter;
import com.example.karyawan.R;
import com.example.karyawan.Utils.GlobalVars;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private AbsentAdapter absentAdapter;
    private List<Absent> absentList;
    private Absent absent;
    private Gson gson;
    public SharedPreferences pref, prf;

    String id_krw;

    private static final String TAG = AttendanceActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);

        List<Absent> results = new ArrayList<>();
        results.add(absent);

        gson = new Gson();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        absentAdapter = new AbsentAdapter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AttendanceActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        if (id_krw == null) {
            loadAbsenHrd();
            recyclerView.setAdapter(absentAdapter);
        } else {

            loadAbsent(id_krw);
            recyclerView.setAdapter(absentAdapter);
        }
//        recyclerView.setAdapter(absentAdapter);

    }

    void loadAbsent(String id_krw) {
        if (absentAdapter != null)
            absentAdapter.clearAll();

            AndroidNetworking.get(GlobalVars.BASE_IP + "absen?id_kar=" + id_krw)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<Absent> result = new ArrayList<>();
                            try {

                                Log.e("tampil = ", response.toString(1));

                                if (result != null)
                                    result.clear();

                                String message = response.getString("message");

                                if (message.equals("Absent were found")) {
                                    String records = response.getString("data");
                                    JSONArray datArr = new JSONArray(records);

                                    if (datArr.length() > 0) {

                                        for (int i = 0; i < datArr.length(); i++) {
                                            Absent absent = gson.fromJson(datArr.getJSONObject(i).toString(), Absent.class);
                                            result.add(absent);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            absentAdapter.addAll(result);
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
    }

    private void  loadAbsenHrd() {

        AndroidNetworking.get(GlobalVars.BASE_IP + "absen")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Absent> result = new ArrayList<>();
                        try {

                            Log.e("tampil = ", response.toString(1));

                            if (result != null)
                                result.clear();

                            String message = response.getString("message");

                            if (message.equals("Absent were found")) {
                                String records = response.getString("data");
                                JSONArray datArr = new JSONArray(records);

                                if (datArr.length() > 0) {

                                    for (int i = 0; i < datArr.length(); i++) {
                                        Absent absent = gson.fromJson(datArr.getJSONObject(i).toString(), Absent.class);
                                        result.add(absent);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        absentAdapter.addAll(result);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
