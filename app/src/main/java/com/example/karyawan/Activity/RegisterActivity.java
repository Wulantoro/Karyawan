package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog;
import com.example.karyawan.R;
import com.example.karyawan.Model.Divisi;
import com.example.karyawan.Utils.GlobalVars;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private ImageView imgtgl;
    private TextView ettgl;
    private Spinner spdivisi;

    private Gson gson;

    private static String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        gson = new Gson();
        imgtgl = findViewById(R.id.imgtgl);
        ettgl = findViewById(R.id.ettgl);
        spdivisi = findViewById(R.id.spdivisi);

        DateFormat simpleDate;
        Date date;

        date = Calendar.getInstance().getTime();
        simpleDate = new SimpleDateFormat("ddMMyyyy");

        //Function Tanggal
        imgtgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateSpinner();
            }
        });

        //get Divisi
        loadDiv();
    }

    private void loadDiv() {

        Log.e(TAG, "ip >> " + GlobalVars.BASE_IP + "divisi");

        AndroidNetworking.post(GlobalVars.BASE_IP + "divisi")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Divisi> result = new ArrayList<>();

                        try {
                            Log.e(TAG, "divisi = " + response.toString(1));
                            if (result != null)
                                result.clear();

                            String message = response.getString("message");

                            if (message.equals("Divisi were found")) {
                                String records = response.getString("data");

                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        Divisi divisi = gson.fromJson(dataArr.getJSONObject(i).toString(), Divisi.class);
                                        result.add(divisi);
                                        Log.e(TAG, "dividsi >> " +divisi.getNmDivisi());
                                    }
                                    setDivisi(result);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONException "+e, Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "ANError "+ anError, Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private String div;
    private void setDivisi(final List<Divisi> divisiList) {
        ArrayAdapter<Divisi> voteTypeAdapter = new ArrayAdapter<Divisi>(getApplicationContext(), R.layout.divisi_spinner, divisiList) {

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position,convertView, parent);
                textView.setText(divisiList.get(position).getNmDivisi());

                return textView;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(divisiList.get(position).getNmDivisi());

                return textView;
            }
        };

        spdivisi.setAdapter(voteTypeAdapter);
        spdivisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                div = divisiList.get(i).getNmDivisi().toString();
                Log.e(TAG, "div = " + divisiList.get(i).getNmDivisi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void DateSpinner() {
        final SpinnerPickerDialog spinnerPickerDialog = new SpinnerPickerDialog();
        spinnerPickerDialog.setContext(this);
        spinnerPickerDialog.setAllColor(ContextCompat.getColor(this, R.color.greenMeetAp));
        spinnerPickerDialog.setmTextColor(Color.BLACK);
        spinnerPickerDialog.setArrowButton(true);
        spinnerPickerDialog.setOnDialogListener(new SpinnerPickerDialog.OnDialogListener() {
            @Override
            public void onSetDate(int month, int day, int year) {
                month = month + 1;
//                String date = day + "-" + month + "-" + year;
                String date = year + "-" + month + "-" + day;
                DateFormat dp_medium = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                String dp_medium_uk_strg = dp_medium.format(Calendar.getInstance().getTime());
                ettgl.setText(date);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onDismiss() {

            }
        });

        spinnerPickerDialog.show(this.getSupportFragmentManager(), "");
    }
}
