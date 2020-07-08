package com.example.karyawan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.karyawan.Activity.DetApprCutiActivity;
import com.example.karyawan.Activity.DetailCutiActivity;
import com.example.karyawan.Model.Cuti;
import com.example.karyawan.R;
import com.jackandphantom.circularimageview.RoundedImage;

import java.util.ArrayList;
import java.util.List;

public class ApprCutiAdapter extends RecyclerView.Adapter<ApprCutiAdapter.ViewHolder> {
    private List<Cuti> list;
    private Context context;

    public ApprCutiAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ApprCutiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_aprcuti, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Cuti cuti = list.get(holder.getAdapterPosition());
        holder.tvnama1.setText(list.get(position).getNama_krw());
        holder.tvsts1.setText(list.get(position).getStatus());
        holder.tvlama.setText(list.get(position).getLama_cuti());

        Glide.with(context)
                .load(cuti.getImage_file())
                .into(holder.civcuti);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetApprCutiActivity.class);
                intent.putExtra("key_cuti", cuti);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Cuti cuti) {
        list.add(cuti);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<Cuti> moveResult) {
        for (Cuti result : moveResult) {
            add(result);
        }
    }

    private void remove(Cuti cuti) {
        int position = list.indexOf(cuti);
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

    private Cuti getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImage civcuti;
        private TextView tvnama1;
        private TextView tvsts1;
        private TextView tvlama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            civcuti = itemView.findViewById(R.id.civcuti);
            tvnama1 = itemView.findViewById(R.id.tvnama1);
            tvsts1 = itemView.findViewById(R.id.tvsts1);
            tvlama = itemView.findViewById(R.id.tvlama);

        }
    }
}
