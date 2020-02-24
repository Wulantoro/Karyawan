package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.karyawan.Activity.Model.Absent;
import com.example.karyawan.R;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AbsentAdapter absentAdapter;
    private ArrayList<Absent> absentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        loadAbsent();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        absentAdapter = new AbsentAdapter(absentArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AttendanceActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(absentAdapter);
    }

    void loadAbsent() {
        absentArrayList = new ArrayList<>();
        absentArrayList.add(new Absent("Dimas Maulana", "07:45", "17:01", "12-02-2020"));
        absentArrayList.add(new Absent("Dimas Maulana", "07:30", "17:15", "12-02-2020"));
        absentArrayList.add(new Absent("Dimas Maulana", "07:33", "17:30", "12-02-2020"));
        absentArrayList.add(new Absent("Dimas Maulana", "07:30", "17:14", "12-02-2020"));
    }
}
