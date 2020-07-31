package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.karyawan.Model.Cuti;
import com.example.karyawan.R;

public class DetailCutiActivity extends AppCompatActivity {

    private TextView tvnama1;
    private TextView tvdivisi1;
    private TextView tvjenis1;
    private TextView tvmulai1;
    private TextView tvselesai1;
    private TextView tvlama1;
    private TextView tvsts1;
    private TextView tvket1;
    private Spinner spaprmgr;
    private Button btnedit;

    private Cuti cuti;

    String id_cuti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cuti);

        tvnama1 = findViewById(R.id.tvnama1);
        tvdivisi1 = findViewById(R.id.tvdivisi1);
        tvjenis1 = findViewById(R.id.tvjenis1);
        tvmulai1 = findViewById(R.id.tvmulai1);
        tvselesai1 = findViewById(R.id.tvselesai1);
        tvlama1 = findViewById(R.id.tvlama1);
        tvsts1 = findViewById(R.id.tvsts1);
        tvket1 = findViewById(R.id.tvket1);
        spaprmgr = findViewById(R.id.spaprmgr);
        btnedit = findViewById(R.id.btnedit);

        cuti = getIntent().getParcelableExtra("key_cuti");
        tvnama1.setText(cuti.getNama_krw());
        tvdivisi1.setText(cuti.getDivisi());
        tvjenis1.setText(cuti.getJns_cuti());
        tvmulai1.setText(cuti.getTgl_mulai());
        tvselesai1.setText(cuti.getTgl_selesai());
        tvlama1.setText(cuti.getLama_cuti());
//        tvsts1.setText(cuti.getStatus());
        tvket1.setText(cuti.getAlasan());
        id_cuti = cuti.getId_cuti();
    }
}