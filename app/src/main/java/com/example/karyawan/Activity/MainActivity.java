package com.example.karyawan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.karyawan.Model.Karyawan;
import com.example.karyawan.R;
import com.example.karyawan.Utils.AppPermission;
import com.example.karyawan.Utils.GlobalHelper;
import com.example.karyawan.Utils.GlobalVars;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] ALL_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 901;
    private static final int READ_EXTERNAL_STORAGE_CODE = 902;
    private static final int CAMERA_CODE = 904;
    private static final int ACCESS_CALL_PHONE = 903;
    private static final int ALL_REQUEST_CODE = 999;
    private AppPermission mRuntimePermission;

    private Button btnLogin;
    private TextView tvDaftar;
    private TextView etusername;
    private TextView etpassword;
    public SharedPreferences pref, prf;

    String id_krw;
    String divisi;

    private Gson gson;

    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRuntimePermission = new AppPermission(this);

        if (mRuntimePermission.hasPermission(ALL_PERMISSIONS)) {
            GlobalHelper.createFolder();
        }else{
            mRuntimePermission.requestPermission(this, ALL_PERMISSIONS, ALL_REQUEST_CODE);
        }

        gson = new Gson();

        btnLogin = findViewById(R.id.btnLogin);
        tvDaftar = findViewById(R.id.tvDaftar);
        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkLogin() {

        if (etusername.getText().toString().isEmpty()) {
            Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (etpassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {

            final JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("username_krw", etusername.getText().toString());
                jsonObject.put("password", etpassword.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AndroidNetworking.post(GlobalVars.BASE_IP + "login")
                    //.addJSONObjectBody(jsonObject)
                    .addBodyParameter("username_krw", etusername.getText().toString())
                    .addBodyParameter("password", etpassword.getText().toString())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<Karyawan> results = new ArrayList<>();
//                        Log.e(TAG, "onResponse11111111 = " +response);
                            try {
                                String message = response.getString("message");
                                String success = response.getString("success");
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                                if (success.equals("1")) {

                                    String user = response.getString("data");
                                    JSONArray dataArr = new JSONArray(user);
//
                                    if (dataArr.length() > 0) {
                                        for (int i = 0; i < dataArr.length(); i++) {
                                            Karyawan karyawan = gson.fromJson(dataArr.getJSONObject(i).toString(), Karyawan.class);
                                            results.add(karyawan);

                                            if (karyawan.getDivisi().equalsIgnoreCase("HRD")) {
                                                Intent intent = new Intent(getApplicationContext(), HomeHrdActivity.class);
                                                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                                                id_krw = karyawan.getIdKrw();
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("id_krw", id_krw);
                                                editor.commit();
                                                startActivity(intent);
                                            } else if(karyawan.getLevel().equalsIgnoreCase("1")){
                                                Intent intent = new Intent(getApplicationContext(), HomeMgrActivity.class);
                                                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                                                id_krw = karyawan.getIdKrw();
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("id_krw", id_krw);
                                                editor.commit();

                                                pref = getSharedPreferences("Divisi", MODE_PRIVATE);
                                                divisi = karyawan.getDivisi();
                                                SharedPreferences.Editor editor1 = pref.edit();
                                                editor1.putString("divisi", divisi);
                                                editor1.commit();

                                                startActivity(intent);

                                            } else if (karyawan.getLevel().equalsIgnoreCase("2") || karyawan.getLevel().equalsIgnoreCase("3")){
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
                                                id_krw = karyawan.getIdKrw();
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("id_krw", id_krw);
                                                editor.commit();
                                                startActivity(intent);
                                            }

                                        }
                                    }

                                } else {
                                    Toast.makeText(MainActivity.this, "Password atau Username salah", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "JSONExceptions" + e, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(MainActivity.this, "Gagal Login, Username atau Password salah ", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "error = " + anError);

                        }
                    });
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_REQUEST_CODE:
                List<Integer> permissionResults = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults.add(grantResult);
                }
                if (permissionResults.contains(PackageManager.PERMISSION_DENIED)) {
                    Toast.makeText(this, "Semua permintaan ditolak", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Semua permintaan diizinkan", Toast.LENGTH_SHORT).show();
                }
                break;
            case WRITE_EXTERNAL_STORAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Tulis permintaan penyimpanan eksternal tidak diizinkan", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Write External Storage Permissions granted", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case READ_EXTERNAL_STORAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Baca permintaan penyimpanan eksternal tidak diizinkan", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Read External Storage Permissions granted", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case CAMERA_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Permintaan Camera diizinkan", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Camera Permissions granted", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
        }
    }
}
