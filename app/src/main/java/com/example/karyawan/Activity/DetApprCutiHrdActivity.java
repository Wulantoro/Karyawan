package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.karyawan.Model.Cuti;
import com.example.karyawan.R;
import com.example.karyawan.Utils.GlobalVars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetApprCutiHrdActivity extends AppCompatActivity {

    private TextView tvnama1;
    private TextView tvdivisi1;
    private TextView tvjenis1;
    private TextView tvmulai1;
    private TextView tvselesai1;
    private TextView tvlama1;
    private TextView tvket1;
    private TextView tvaccmgr1;
    private Spinner spaprhrd;
    private Button btnSimpan;

    private Cuti cuti;

    String id_cuti;
    int id_kar;
    private String[] acc_mgr = {
            "--Pilih--",
            "Tolak",
            "ACC_HRD",
    };

    final static String TAG = DetApprCutiActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det_appr_cuti_hrd);

        tvnama1 = findViewById(R.id.tvnama1);
        tvdivisi1 = findViewById(R.id.tvdivisi1);
        tvjenis1 = findViewById(R.id.tvjenis1);
        tvmulai1 = findViewById(R.id.tvmulai1);
        tvselesai1 = findViewById(R.id.tvselesai1);
        tvlama1 = findViewById(R.id.tvlama1);
        tvket1 = findViewById(R.id.tvket1);
//        tvaccmgr1 = findViewById(R.id.tvaccmgr1);
        spaprhrd = findViewById(R.id.spaprhrd);
        btnSimpan = findViewById(R.id.btnSimpan);

        cuti = getIntent().getParcelableExtra("key_cuti");
        tvnama1.setText(cuti.getNama_krw());
        tvdivisi1.setText(cuti.getDivisi());
        tvjenis1.setText(cuti.getJns_cuti());
        tvmulai1.setText(cuti.getTgl_mulai());
        tvselesai1.setText(cuti.getTgl_selesai());
        tvlama1.setText(cuti.getLama_cuti());
        tvket1.setText(cuti.getAlasan());
//        tvaccmgr1.setText(cuti.getAcc());
        id_cuti = cuti.getId_cuti();
        id_kar = Integer.parseInt(cuti.getId_kar());
        Log.e(TAG, "id kar " + id_kar);

        spaprhrd.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, acc_mgr));

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accHrd();
            }
        });
    }

    private void accHrd() {

        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("id_cuti", id_cuti);
            jsonObject.put("id_kar", id_kar);
            jsonObject.put("jns_cuti", tvjenis1.getText().toString());
            jsonObject.put("tgl_mulai", tvmulai1.getText().toString());
            jsonObject.put("tgl_selesai", tvselesai1.getText().toString());
            jsonObject.put("lama_cuti", tvlama1.getText().toString());
            jsonObject.put("alasan", tvket1.getText().toString() );
//            jsonObject.put("status", tvsts1.getText().toString());
            jsonObject.put("acc", spaprhrd.getItemAtPosition(spaprhrd.getSelectedItemPosition()).toString());

            newArr.put(jsonObject);

            Log.e(TAG, "coba input = " + newArr.toString(1));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.put(GlobalVars.BASE_IP + "approve?id_cuti")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ApprovalCutiHrdActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptions" + e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Cuti gagal diapprove", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}