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
import com.example.karyawan.Adapter.ApprCutiHrdAdapter;
import com.example.karyawan.Model.Cuti;
import com.example.karyawan.R;
import com.example.karyawan.Utils.GlobalVars;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApprovalCutiHrdActivity extends AppCompatActivity {

    public SharedPreferences pref, prf;

    private RecyclerView rvaprcutihrd;
    private ApprCutiHrdAdapter apprCutiHrdAdapter;
    private Cuti cuti;
    private Gson gson;

    String id_krw;
    String divisi;

    private String TAG = ApprovalActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_cuti_hrd);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id kar " + id_krw);

        gson = new Gson();
        rvaprcutihrd = findViewById(R.id.rvaprcutihrd);
        apprCutiHrdAdapter = new ApprCutiHrdAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ApprovalCutiHrdActivity.this);
        rvaprcutihrd.setLayoutManager(layoutManager);
        loadCuti();
        rvaprcutihrd.setAdapter(apprCutiHrdAdapter);

    }

    public void loadCuti() {
        if (apprCutiHrdAdapter != null)
            apprCutiHrdAdapter.clearAll();
        Log.e(TAG, "ip " + GlobalVars.BASE_IP + "approve/hrd");

        AndroidNetworking.get(GlobalVars.BASE_IP + "approve/hrd")
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
                        apprCutiHrdAdapter.addAll(result);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}