package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.karyawan.Adapter.KaryawanAdapter;
import com.example.karyawan.Model.Karyawan;
import com.example.karyawan.R;
import com.example.karyawan.Utils.GlobalHelper;
import com.example.karyawan.Utils.GlobalVars;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KaryawanActivity extends AppCompatActivity {

    private RecyclerView rvkaryawan;
    private KaryawanAdapter karyawanAdapter;
    private Karyawan karyawan;
    private Gson gson;
    public SharedPreferences pref, prf;

    private static final String TAG = KaryawanActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyawan);

        List<Karyawan> result = new ArrayList<>();
        result.add(karyawan);

        gson = new Gson();
        rvkaryawan = findViewById(R.id.rvkaryawan);
        karyawanAdapter = new KaryawanAdapter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(KaryawanActivity.this);
        rvkaryawan.setLayoutManager(layoutManager);

        loadKaryawan();
        rvkaryawan.setAdapter(karyawanAdapter);
    }

    private void loadKaryawan() {
        if (karyawanAdapter != null)
            karyawanAdapter.clearAll();

        AndroidNetworking.get(GlobalVars.BASE_IP + "karyawan")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Karyawan> result = new ArrayList<>();
                        try {

                            Log.e("tampil = ", response.toString(1));

                            if (result != null)
                                result.clear();

                            String message = response.getString("message");

                            if (message.equals("Karyawan Ditemukan")) {
                                String records = response.getString("data");
                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {

                                    for (int i = 0; i < dataArr.length(); i++) {
                                        Karyawan karyawan = gson.fromJson(dataArr.getJSONObject(i).toString(), Karyawan.class);
                                        result.add(karyawan);
                                    }
                                }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        karyawanAdapter.addAll(result);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
