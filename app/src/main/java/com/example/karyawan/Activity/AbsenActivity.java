package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.karyawan.R;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class AbsenActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }


    public void onScanned(Barcode barcode) {

        Intent intent = new Intent(AbsenActivity.this, AttendanceActivity.class);
        intent.putExtra("wccode", barcode.displayValue);
        startActivity(intent);

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

}
