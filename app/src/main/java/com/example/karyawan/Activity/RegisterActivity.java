package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog;
import com.example.karyawan.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private ImageView imgtgl;
    private TextView ettgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgtgl = findViewById(R.id.imgtgl);
        ettgl = findViewById(R.id.ettgl);

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
