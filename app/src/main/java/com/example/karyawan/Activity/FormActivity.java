package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog;
import com.example.karyawan.Adapter.MenuAdapter;
import com.example.karyawan.Interface.RecyclerViewClick;
import com.example.karyawan.Model.ApproveMenu;
import com.example.karyawan.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {

    private Spinner spJenis;
    private ImageView imgTglmulai;
    private EditText etTglmulai;
    private EditText etTglselesai;
    private ImageView ivTglselesai;
    private TextView tvlama;
    private Button btnSimpan;

    List<ApproveMenu> list =new ArrayList<>();

    private RecyclerView rvMenu;
    private MenuAdapter menuAdapter;
    private LinearLayout parentLayout;

    private ApproveMenu menu;
    private Intent intent;


    private String[] jenis = {
            "--Pilih--",
            "Cuti",
            "Sakit",
            "Izin"
    };
    private static final String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

//        spJenis = findViewById(R.id.spJenis);
//        imgTglmulai = findViewById(R.id.imgTglmulai);
//        etTglmulai = findViewById(R.id.etTglmulai);
//        etTglselesai = findViewById(R.id.etTglselesai);
//        ivTglselesai = findViewById(R.id.ivTglselesai);
//        tvlama = findViewById(R.id.tvlama);
//        btnSimpan = findViewById(R.id.btnSimpan);

        rvMenu = findViewById(R.id.rvMenu);
        parentLayout = findViewById(R.id.parentLayout);

        approveMenu();

        // spinner jenis
//        spJenis.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, jenis));

        //function tanggal mulai
//        imgTglmulai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tanggalMulai();
//
//            }
//        });
//
//        //function tanggal selesai
//        ivTglselesai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tanggalSelesai();
//            }
//        });
//
//        btnSimpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }


    private void approveMenu() {
        //0
        menu = new ApproveMenu(R.string.cuti);
        list.add(menu);
        //1
        menu = new ApproveMenu(R.string.izin);
        list.add(menu);
        //2
        menu = new ApproveMenu(R.string.sakit);
        list.add(menu);

        menuAdapter = new MenuAdapter(this, list, new RecyclerViewClick() {
            @Override
            public void onItemClick(View v, int position) {
                if (position == 0) {
                    intentToCuti();
                } else if (position == 1) {
                    intenToIzin();
                }else if (position == 2) {
                    intenToSakit();
                }
            }
        });

        rvMenu.setHasFixedSize(true);
        rvMenu.setAdapter(menuAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMenu.setLayoutManager(linearLayoutManager);
        rvMenu.setItemAnimator(new DefaultItemAnimator());

    }

    private void intentToCuti() {
        intent = new Intent(getApplicationContext(), CutiActivity.class);
        startActivity(intent);
    }

    private void intenToIzin() {
        intent = new Intent(getApplicationContext(), IzinActivity.class);
        startActivity(intent);
    }

    private void intenToSakit() {
        intent = new Intent(getApplicationContext(), SakitActivity.class);
        startActivity(intent);
    }

    //function tanggalMulai
//    private void tanggalMulai(){
//        final SpinnerPickerDialog spinnerPickerDialog = new SpinnerPickerDialog();
//        spinnerPickerDialog.setContext(this);
//        spinnerPickerDialog.setAllColor(ContextCompat.getColor(this, R.color.greenMeetAp));
//        spinnerPickerDialog.setmTextColor(Color.BLACK);
//        spinnerPickerDialog.setArrowButton(true);
//        spinnerPickerDialog.setOnDialogListener(new SpinnerPickerDialog.OnDialogListener() {
//            @Override
//            public void onSetDate(int month, int day, int year) {
//                month = month + 1;
////                String date = day + "-" + month + "-" + year;
//                String date = year + "-" + month + "-" + day;
//                DateFormat dp_medium = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
//                String dp_medium_uk_strg = dp_medium.format(Calendar.getInstance().getTime());
//                etTglmulai.setText(date);
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onDismiss() {
//
//            }
//        });
//
//        spinnerPickerDialog.show(this.getSupportFragmentManager(), "");
//    }
//
//    //function tanggal selesai
//    private void tanggalSelesai() {
//        final SpinnerPickerDialog spinnerPickerDialog = new SpinnerPickerDialog();
//        spinnerPickerDialog.setContext(this);
//        spinnerPickerDialog.setAllColor(ContextCompat.getColor(this, R.color.greenMeetAp));
//        spinnerPickerDialog.setmTextColor(Color.BLACK);
//        spinnerPickerDialog.setArrowButton(true);
//        spinnerPickerDialog.setOnDialogListener(new SpinnerPickerDialog.OnDialogListener() {
//            @Override
//            public void onSetDate(int month, int day, int year) {
//                month = month + 1;
//                String date = year + "-" + month + "-" + day;
//                DateFormat dp_medium = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
//                String dp_medium_uk_strg = dp_medium.format(Calendar.getInstance().getTime());
//                etTglselesai.setText(date);
//                lamaHari();
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onDismiss() {
//
//            }
//        });
//
//        spinnerPickerDialog.show(this.getSupportFragmentManager(), "");
//
//    }
//
//    //function lama hari
//    private void lamaHari() {
//        String stglAwal = etTglmulai.getText().toString();
//        String stglAkhir = etTglselesai.getText().toString();
//        DateFormat dateAwal = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat dateAkhir = new SimpleDateFormat("yyyy-MM-dd");
//
//        try {
//            Date tglAwal = dateAwal.parse(stglAwal);
//            Date tglAkhir = dateAkhir.parse(stglAkhir);
//
//            Date TGLAwal = tglAwal;
//            Date TGLAkhir = tglAkhir;
//            Calendar cal1 = Calendar.getInstance();
//            cal1.setTime(TGLAwal);
//            Calendar cal2 = Calendar.getInstance();
//            cal2.setTime(TGLAkhir);
//
//            String hasil = String.valueOf(daysBetween(cal1, cal2));
////            tvlama.setText(hasil);
//
//            int numberOfDays = 1;
//            while (cal1.before(cal2)) {
//                if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK)) && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
//                     numberOfDays++;
//                }
//                cal1.add(Calendar.DATE, 1);
//            }
//            tvlama.setText(String.valueOf(numberOfDays));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static long daysBetween(Calendar tanggalAwal, Calendar tanggalAkhir) {
//        long lama = 0;
//        Calendar tanggal = (Calendar) tanggalAwal.clone();
//        while (tanggal.before(tanggalAkhir)) {
//            tanggal.add(Calendar.DAY_OF_MONTH, 1);
//            lama++;
//        }
//        return lama;
//    }


}
