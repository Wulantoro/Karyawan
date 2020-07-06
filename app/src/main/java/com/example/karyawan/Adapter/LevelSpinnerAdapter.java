package com.example.karyawan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.karyawan.Model.Level;
import com.example.karyawan.R;

import java.util.List;

public class LevelSpinnerAdapter extends ArrayAdapter<String> {
    private final LayoutInflater layoutInflater;
    private final Context mContext;
    private final List<Level> items;
    private final int mResource;

    public LevelSpinnerAdapter(@NonNull Context context, int resource, List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        items = objects;
        mResource = resource;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    public View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = layoutInflater.inflate(mResource, parent, false);
        TextView tvspinner = view.findViewById(R.id.tvSpinner);

        Level level = items.get(position);

        if (position == 0) {
            tvspinner.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
        tvspinner.setText(level.getId_level());

        return view;
    }


}
