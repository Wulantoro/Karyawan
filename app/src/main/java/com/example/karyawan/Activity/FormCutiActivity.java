package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.karyawan.Utils.GlobalVars;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormCutiActivity extends AppCompatActivity {

    private Spinner spJenis;
    private ImageView imgTglmulai;
    private EditText etTglmulai;
    private EditText etTglselesai;
    private ImageView ivTglselesai;
    private TextView tvlama;
    private Button btnSimpan;
    private TextInputEditText keterangan;
    public SharedPreferences pref, prf;

    String id_krw;

    private String[] jenis = {
            "--Pilih--",
            "Cuti Melahirkan",
            "Cuti Pribadi",
    };

    private static String TAG = FormCutiActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cuti);

        spJenis = findViewById(R.id.spJenis);
        imgTglmulai = findViewById(R.id.imgTglmulai);
        etTglmulai = findViewById(R.id.etTglmulai);
        etTglselesai = findViewById(R.id.etTglselesai);
        ivTglselesai = findViewById(R.id.ivTglselesai);
        tvlama = findViewById(R.id.tvlama);
        keterangan = findViewById(R.id.keterangan);
        btnSimpan = findViewById(R.id.btnSimpan);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);

//         spinner jenis cuti
        spJenis.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, jenis));

//        function tanggal mulai
        imgTglmulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tanggalMulai();
            }
        });

        //function tanggal selesai
        ivTglselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tanggalSelesai();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanCuti();
            }
        });
    }

//    function tanggalMulai
    private void tanggalMulai(){
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
                etTglmulai.setText(date);

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

    //function tanggal selesai
    private void tanggalSelesai() {
        final SpinnerPickerDialog spinnerPickerDialog = new SpinnerPickerDialog();
        spinnerPickerDialog.setContext(this);
        spinnerPickerDialog.setAllColor(ContextCompat.getColor(this, R.color.greenMeetAp));
        spinnerPickerDialog.setmTextColor(Color.BLACK);
        spinnerPickerDialog.setArrowButton(true);
        spinnerPickerDialog.setOnDialogListener(new SpinnerPickerDialog.OnDialogListener() {
            @Override
            public void onSetDate(int month, int day, int year) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                DateFormat dp_medium = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                String dp_medium_uk_strg = dp_medium.format(Calendar.getInstance().getTime());
                etTglselesai.setText(date);
                lamaHari();
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

    //function lama hari
    private void lamaHari() {
        String stglAwal = etTglmulai.getText().toString();
        String stglAkhir = etTglselesai.getText().toString();
        DateFormat dateAwal = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateAkhir = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date tglAwal = dateAwal.parse(stglAwal);
            Date tglAkhir = dateAkhir.parse(stglAkhir);

            Date TGLAwal = tglAwal;
            Date TGLAkhir = tglAkhir;
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(TGLAwal);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(TGLAkhir);

            String hasil = String.valueOf(daysBetween(cal1, cal2));
//            tvlama.setText(hasil);

            int numberOfDays = 1;
            while (cal1.before(cal2)) {
                if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK)) && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                     numberOfDays++;
                }
                cal1.add(Calendar.DATE, 1);
            }
            tvlama.setText(String.valueOf(numberOfDays));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static long daysBetween(Calendar tanggalAwal, Calendar tanggalAkhir) {
        long lama = 0;
        Calendar tanggal = (Calendar) tanggalAwal.clone();
        while (tanggal.before(tanggalAkhir)) {
            tanggal.add(Calendar.DAY_OF_MONTH, 1);
            lama++;
        }
        return lama;
    }

    private void simpanCuti() {
        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray();

            jsonObject.put("id_kar", id_krw);
            jsonObject.put("jns_cuti", spJenis.getItemAtPosition(spJenis.getSelectedItemPosition()).toString());
            jsonObject.put("tgl_mulai", etTglmulai.getText().toString());
            jsonObject.put("tgl_selesai", etTglselesai.getText().toString());
            jsonObject.put("lama_cuti", tvlama.getText().toString());
            jsonObject.put("alasan", keterangan.getText().toString());
//            jsonObject.put("status", "menunggu");

            jsonArray.put(jsonObject);

            Log.e(TAG, "Coba input cuti = " + jsonArray.toString(1));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(GlobalVars.BASE_IP + "cuti")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(FormCutiActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CutiActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(FormCutiActivity.this, "JSONException" + e, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(FormCutiActivity.this, "Gagal input cuti", Toast.LENGTH_SHORT).show();


                    }
                });
    }
}