package com.example.karyawan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.karyawan.Model.Karyawan;
import com.example.karyawan.R;

import java.util.ArrayList;
import java.util.List;

public class KaryawanAdapter extends RecyclerView.Adapter<KaryawanAdapter.KaryawanViewHolder> {

    private List<Karyawan> list;
    private Context context;

    private static final String TAG = KaryawanAdapter.class.getName();

    public KaryawanAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }


    @NonNull
    @Override
    public KaryawanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_karyawan, parent, false);
        return new KaryawanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KaryawanViewHolder holder, int position) {
        final Karyawan karyawan = list.get(holder.getAdapterPosition());

        holder.tvnama.setText(list.get(position).getNamaKrw());
        holder.tvdivisi.setText(list.get(position).getDivisi());

        Glide.with(context)
                .load(karyawan.getImageFile())
                .into(holder.ivprofil);

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void add(Karyawan r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll (List<Karyawan> moveResults) {
        for (Karyawan result : moveResults) {
            add(result);
        }
    }

    private void remove(Karyawan r) {
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

    private Karyawan getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    public class KaryawanViewHolder extends RecyclerView.ViewHolder {
        private TextView tvnama, tvdivisi;
        private ImageView ivprofil;
        public KaryawanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnama = itemView.findViewById(R.id.tvnama);
            tvdivisi = itemView.findViewById(R.id.tvdivisi);
            ivprofil = itemView.findViewById(R.id.ivprofil);

        }
    }
}
