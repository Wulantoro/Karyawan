package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.karyawan.Utils.GlobalVars;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView tvDaftar;
    private TextView etusername;
    private TextView etpassword;
    public SharedPreferences pref, prf;

    String id_krw;

    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username_krw", etusername.getText().toString());
            jsonObject.put("password", etpassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(GlobalVars.BASE_IP + "login")
                .addJSONObjectBody(jsonObject)
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
                                            startActivity(intent);
                                        } else {
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
                        Toast.makeText(MainActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
