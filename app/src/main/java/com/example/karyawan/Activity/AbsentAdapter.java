package com.example.karyawan.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.karyawan.Activity.Model.Absent;
import com.example.karyawan.R;

import java.util.ArrayList;

public class AbsentAdapter extends RecyclerView.Adapter<AbsentAdapter.AbsentViewHolder> {


    private ArrayList<Absent> dataList;

    public AbsentAdapter(ArrayList<Absent> dataList) {
        this.dataList = dataList;
    }

    @Override

    public AbsentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_absent, parent, false);
        return new AbsentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AbsentViewHolder holder, int position) {
        holder.tvnama.setText(dataList.get(position).getNama());
        holder.tvjammasuk.setText(dataList.get(position).getJamMasuk());
        holder.tvjamkeluar.setText(dataList.get(position).getJamKeluar());
        holder.tvtanggal.setText(dataList.get(position).getTanggal());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AbsentViewHolder extends RecyclerView.ViewHolder{
        private TextView tvnama, tvjammasuk, tvjamkeluar, tvtanggal;

        public AbsentViewHolder(View itemView) {
            super(itemView);
            tvnama = (TextView) itemView.findViewById(R.id.tvnama);
            tvjammasuk = (TextView) itemView.findViewById(R.id.tvjammasuk);
            tvjamkeluar = (TextView) itemView.findViewById(R.id.tvjamkeluar);
            tvtanggal = itemView.findViewById(R.id.tvtanggal);
        }
    }

}
