package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.karyawan.Adapter.AbsentAdapter;
import com.example.karyawan.Model.Absent;
import com.example.karyawan.R;
import com.example.karyawan.Utils.GlobalVars;
import com.google.android.gms.vision.barcode.Barcode;
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

import info.androidhive.barcode.BarcodeReader;

public class AbsenActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    BarcodeReader barcodeReader;
    public SharedPreferences pref, prf;
    private Gson gson;
    private AbsentAdapter absentAdapter;

    String id_krw;
    String tanggal;
    String jam_masuk;
    String status_absn;
    String jam_keluar;

    String jammasuk1;
    String id_absn;
    String sts_absen;

    private Button btncoba;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
    String getCurentTime = sdf1.format(c.getTime());
    String getTestTime = "08:00";
    String getTestTime1 = "17:00";

    private static final String TAG = AbsenActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);


         gson = new Gson();
        absentAdapter = new AbsentAdapter(this);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);



//        get tanggal
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String getCurentDate = sdf.format(c.getTime());
        tanggal =  getCurentDate;
        Log.e(TAG, "Tanggal = " + tanggal);

        //get time
        String jam = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        jam_masuk = jam;
        Log.e(TAG, "jam masuk = " + jam_masuk);

        loadAbsen(id_krw);

        if (getCurentTime.compareTo(getTestTime) > 0 && getTestTime1.compareTo(getCurentTime) > 0) {
            status_absn = "TERLAMBAT";
        } else {
            status_absn = "";
        }
        Log.e(TAG, "status absen = " + status_absn);

        if (getCurentTime.compareTo(getTestTime1) > 0) {
            jam_keluar = jam;
        } else {
            jam_keluar = "";
        }
    }


    public void onScanned(Barcode barcode) {

        if (getCurentTime.compareTo(getTestTime) > 0 && getTestTime1.compareTo(getCurentTime) > 0) {
            absen();
        } else {
            pulang();
//            startActivity(new Intent(getApplicationContext(), AttendanceActivity.class));
        }

    }

    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }

    public void onCameraPermissionDenied() {
        finish();
    }


    private void absen() {
        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("status", 1);
            jsonObject.put("jam_masuk", jam_masuk);
            jsonObject.put("jam_keluar", jam_keluar);
            jsonObject.put("status_absn", status_absn);
            jsonObject.put("tgl_absen", tanggal );
            jsonObject.put("id_kar", id_krw);

            newArr.put(jsonObject);
            Log.e(TAG,"coba absen = "+ newArr.toString(1));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(GlobalVars.BASE_IP + "absen")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            String message = response.getString("message");
                            Toast.makeText(AbsenActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AttendanceActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AbsenActivity.this, "JSONExceptions" + e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(AbsenActivity.this, "Gagal absen " + anError, Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void pulang() {

        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("id_absen", id_absn);
            jsonObject.put("status", 2);
            jsonObject.put("jam_masuk", jammasuk1);
            jsonObject.put("jam_keluar", jam_keluar);
            jsonObject.put("status_absn", sts_absen);
            jsonObject.put("tgl_absen", tanggal );
            jsonObject.put("id_kar", id_krw);

            newArr.put(jsonObject);

            Log.e(TAG, "coba input = " + newArr.toString(1));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.put(GlobalVars.BASE_IP + "absen?id_absen")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AttendanceActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptions" + e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Data gagal diedit", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void loadAbsen(String id_krw) {
        if (absentAdapter != null)
            absentAdapter.clearAll();

        AndroidNetworking.get(GlobalVars.BASE_IP + "absen/last?id_kar=" + id_krw)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Absent> result = new ArrayList<>();
                        try {

                            Log.e("tampil = ", response.toString(1));

                            if (result != null)
                                result.clear();

                            String message = response.getString("message");

                            if (message.equals("Absent were found")) {
                                String records = response.getString("data");
                                JSONArray datArr = new JSONArray(records);

                                if (datArr.length() > 0) {

                                    for (int i = 0; i < datArr.length(); i++) {
                                        Absent absent = gson.fromJson(datArr.getJSONObject(i).toString(), Absent.class);
                                        result.add(absent);
                                         jammasuk1 = absent.getJamMasuk();
                                         id_absn = absent.getId_absen();
                                         sts_absen = absent.getStatusAbsn();
                                         Log.e(TAG, "jam masuk 1 = " +jammasuk1 );

                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        absentAdapter.addAll(result);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}
