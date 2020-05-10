package com.example.karyawan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.karyawan.Interface.RecyclerViewClick;
import com.example.karyawan.Model.ApproveMenu;
import com.example.karyawan.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    Context context;
    private List<ApproveMenu> list;
    RecyclerViewClick listener;

    public MenuAdapter(Context context, List<ApproveMenu> boardingList, RecyclerViewClick listener) {
        this.context = context;
        this.list = boardingList;
        this.listener = listener;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);
        final MenuAdapter.ViewHolder holder=new MenuAdapter.ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getAdapterPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ApproveMenu menu = list.get(holder.getAdapterPosition());
        holder.tvMenu.setText(menu.getMenu());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMenu = itemView.findViewById(R.id.tvMenu);
        }
    }
}

