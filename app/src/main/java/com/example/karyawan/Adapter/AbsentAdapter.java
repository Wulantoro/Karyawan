package com.example.karyawan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.karyawan.Model.Absent;
import com.example.karyawan.R;

import java.util.ArrayList;
import java.util.List;

public class AbsentAdapter extends RecyclerView.Adapter<AbsentAdapter.AbsentViewHolder> {


    private List<Absent> list;
    private Context context;

    private static final String TAG = AbsentAdapter.class.getName();


    public AbsentAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();

    }

    @Override

    public AbsentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_absent, parent, false);
        return new AbsentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AbsentViewHolder holder, int position) {
        final Absent absent = list.get(holder.getAdapterPosition());

        Log.e(TAG, "dataArr" + absent.getImageFile());

        holder.tvnama.setText(list.get(position).getUsernameKrw());
        holder.tvjammasuk.setText(list.get(position).getJamMasuk());
        holder.tvjamkeluar.setText(list.get(position).getJamKeluar());
        holder.tvtanggal.setText(list.get(position).getTglAbsen());
        holder.tvstsabsen.setText(list.get(position).getStatusAbsn());

        Glide.with(context)
                .load(absent.getImageFile())
                .into(holder.ivprofil);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Absent r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll (List<Absent> moveResults) {
        for (Absent result : moveResults) {
            add(result);
        }
    }

    private void remove(Absent r) {
        int position = list.indexOf(r);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void clearAll() {
        if (!list.isEmpty()) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    private Absent getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    public class AbsentViewHolder extends RecyclerView.ViewHolder{
        private TextView tvnama, tvjammasuk, tvjamkeluar, tvtanggal;
        private TextView tvstsabsen;
        private ImageView ivprofil;

        public AbsentViewHolder(View itemView) {
            super(itemView);
            tvnama = (TextView) itemView.findViewById(R.id.tvnama);
            tvjammasuk = (TextView) itemView.findViewById(R.id.tvjammasuk);
            tvjamkeluar = (TextView) itemView.findViewById(R.id.tvjamkeluar);
            tvtanggal = itemView.findViewById(R.id.tvtanggal);
            tvstsabsen = itemView.findViewById(R.id.tvstsabsen);
            ivprofil = itemView.findViewById(R.id.ivprofil);
        }
    }

}
