package com.example.karyawan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.karyawan.Adapter.AbsentAdapter;
import com.example.karyawan.Model.Absent;
import com.example.karyawan.R;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private AbsentAdapter absentAdapter;
    private ArrayList<Absent> absentArrayList;
    private Absent absent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        List<Absent> results = new ArrayList<>();
        results.add(absent);
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
        absentArrayList.add(new Absent("Dimas Maulana", "07:01", "17:14", "12-02-2020"));

    }
}
