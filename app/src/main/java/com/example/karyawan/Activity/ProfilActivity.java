package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.karyawan.Model.Karyawan;
import com.example.karyawan.R;
import com.example.karyawan.Utils.GlobalVars;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {

    private TextView tvnama;
    private TextView tvtgllahir2;
    private TextView tvjnskelamin;
    private TextView tvalamat2;
    private TextView tvtelp1;
    private CircleImageView imgprofil;
    private ImageView ivedit;


    public SharedPreferences pref, prf;

    String id_krw;
//    private Karyawan karyawan;


    private static final String TAG = ProfilActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tvnama = findViewById(R.id.tvnama);
        tvtgllahir2 = findViewById(R.id.tvtgllahir2);
        tvjnskelamin = findViewById(R.id.tvjnskelamin);
        tvalamat2 = findViewById(R.id.tvalamat2);
        tvtelp1 = findViewById(R.id.tvtelp1);
        imgprofil = findViewById(R.id.imgprofil);
        ivedit = findViewById(R.id.ivedit);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);


        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Karyawan karyawan = new Karyawan();
                Intent intent = new Intent(getApplicationContext(), EditKaryawanActivity.class);
//                intent.putExtra("edit", karyawan);

                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                id_krw =pref.getString("id_krw", null);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_krw", id_krw);
                editor.commit();
                startActivity(intent);
            }
        });

        loadProfil();
    }

    private void loadProfil() {
        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id_krw", id_krw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.get(GlobalVars.BASE_IP + "karyawan?id_krw=" + id_krw)
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
                                tvnama.setText(jsonObject.getString("nama_krw"));
                                tvjnskelamin.setText(jsonObject.getString("gender_krw"));
                                tvtgllahir2.setText(jsonObject.getString("tgllahir_krw"));
                                tvtelp1.setText(jsonObject.getString("telp_krw"));
                                tvalamat2.setText(jsonObject.getString("alamat_krw"));

                                Glide.with(getApplicationContext())
                                        .load(jsonObject.getString("image_file"))
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .centerCrop()
                                        .dontAnimate()
                                        .into(imgprofil);
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
