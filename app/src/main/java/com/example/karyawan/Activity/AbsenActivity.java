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
import com.example.karyawan.R;
import com.example.karyawan.Utils.GlobalVars;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import info.androidhive.barcode.BarcodeReader;

public class AbsenActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    BarcodeReader barcodeReader;

    public SharedPreferences pref, prf;

    String id_krw;
    String tanggal;
    String jam_masuk;
    String status_absn;
    String jam_keluar;

    private Button btncoba;

    private static final String TAG = AbsenActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);



        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);



        //get tanggal
        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        String getCurentDate = sdf.format(c.getTime());
//        tanggal = getCurentDate;
//        Log.e(TAG, "Tanggal = " + tanggal);


        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
       tanggal = date;
        Log.e(TAG, "Tanggal = " + tanggal);

        //get time
        String jam = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        jam_masuk = jam;
        Log.e(TAG, "jam masuk = " + jam_masuk);

        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String getCurentTime = sdf1.format(c.getTime());
        String getTestTime = "08:00";
        String getTestTime1 = "17:00";


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
        absen();
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
            jsonObject.put("tgl_absen", tanggal);
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

}
