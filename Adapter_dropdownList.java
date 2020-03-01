package com.ArashTorDev.tablo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter_dropdownList extends ArrayAdapter<Model_dropdown_list_frame_row> {

    public Adapter_dropdownList(Context context, List<Model_dropdown_list_frame_row> models) {
        super(context, 0, models);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return viewHolder(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return viewHolder(position, convertView, parent);
    }

    private View viewHolder(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_dropdown_list_frame_main_activity, parent, false);
        }
        ImageView im = convertView.findViewById(R.id.imageView_frame_list);
        TextView tvInfo = convertView.findViewById(R.id.tv_information);
        TextView tvPrice = convertView.findViewById(R.id.tv_price);

        Model_dropdown_list_frame_row model = getItem(position);
        if (model != null) {
            im.setImageResource(model.getFrameImage());
            tvInfo.setText(model.getInformation());
            tvPrice.setText(model.getPrice());
        }
        return convertView;
    }
}
