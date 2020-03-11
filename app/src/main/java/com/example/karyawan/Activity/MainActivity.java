package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.karyawan.R;
import com.example.karyawan.Utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView tvDaftar;
    private EditText etusername;
    private EditText etpassword;

    private ProgressDialog progressDialog;
    public SharedPreferences pref, prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        tvDaftar = findViewById(R.id.tvDaftar);
        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
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

    public void checkLogin(final String username, final String password) {
        final SharedPrefManager sharedPrefManager;
        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging in.....");
//        showDialog();

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", etusername.getText().toString());
            jsonObject.put("password", etpassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
