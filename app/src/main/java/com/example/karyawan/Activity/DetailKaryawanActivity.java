package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.karyawan.Model.Karyawan;
import com.example.karyawan.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailKaryawanActivity extends AppCompatActivity {

    private CircleImageView imgprofil;
    private TextView tvnama;
    private TextView tvtgllahir2;
    private TextView tvjnskelamin;
    private TextView tvalamat2;
    private TextView tvtelp1;

    private Karyawan karyawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karyawan);

        imgprofil = findViewById(R.id.imgprofil);
        tvnama = findViewById(R.id.tvnama);
        tvtgllahir2 = findViewById(R.id.tvtgllahir2);
        tvjnskelamin = findViewById(R.id.tvjnskelamin);
        tvalamat2 = findViewById(R.id.tvalamat2);
        tvtelp1 = findViewById(R.id.tvtelp1);

        karyawan = getIntent().getParcelableExtra("key_krw");
        Glide.with(getApplication())
                .load(karyawan.getImageFile())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(imgprofil);

        tvnama.setText(karyawan.getNamaKrw());
        tvjnskelamin.setText(karyawan.getGenderKrw());
        tvtgllahir2.setText(karyawan.getTgllahirKrw());
        tvtelp1.setText(karyawan.getTelpKrw());
        tvalamat2.setText(karyawan.getAlamatKrw());

    }
}
