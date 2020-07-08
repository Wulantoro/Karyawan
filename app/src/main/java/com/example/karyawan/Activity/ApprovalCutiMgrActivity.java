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
import com.example.karyawan.Adapter.ApprCutiAdapter;
import com.example.karyawan.Adapter.CutiAdapter;
import com.example.karyawan.Model.Cuti;
import com.example.karyawan.R;
import com.example.karyawan.Utils.GlobalVars;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApprovalCutiMgrActivity extends AppCompatActivity {

    public SharedPreferences pref, prf;
    private RecyclerView rvaprcuti;
    private ApprCutiAdapter apprCutiAdapter;
    private Cuti cuti;
    private Gson gson;

    String id_krw;
    String divisi;

    private static final String TAG = ApprovalCutiMgrActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_cuti_mgr);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id kar " + id_krw);

        pref = getSharedPreferences("Divisi", MODE_PRIVATE);
        divisi = pref.getString("divisi", null);
        Log.e(TAG, "divisi " + divisi);


        gson = new Gson();
        rvaprcuti = findViewById(R.id.rvaprcuti);
        apprCutiAdapter = new ApprCutiAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ApprovalCutiMgrActivity.this);
        rvaprcuti.setLayoutManager(layoutManager);
        loadCuti(divisi);
        rvaprcuti.setAdapter(apprCutiAdapter);
    }

    public void loadCuti(String divisi) {
        if (apprCutiAdapter != null)
            apprCutiAdapter.clearAll();
        Log.e(TAG, "ip " + GlobalVars.BASE_IP + "approve?divisi=" + divisi);

        AndroidNetworking.get(GlobalVars.BASE_IP + "approve?divisi=" + divisi)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Cuti> result = new ArrayList<>();
                        try {
                            Log.e(TAG, "Cuti = " + response.toString(1));

                            if (result != null)
                                result.clear();

                            String message = response.getString("message");
                            if (message.equalsIgnoreCase("Data Cuti Ditemukan")) {
                                String records = response.getString("data");
                                JSONArray jsonArray = new JSONArray(records);

                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Cuti cuti = gson.fromJson(jsonArray.getJSONObject(i).toString(), Cuti.class);
                                        result.add(cuti);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        apprCutiAdapter.addAll(result);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}