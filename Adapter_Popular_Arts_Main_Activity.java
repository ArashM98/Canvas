package com.ArashTorDev.tablo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Popular_Arts_Main_Activity extends RecyclerView.Adapter<Adapter_Popular_Arts_Main_Activity.ViewHolder> {

    List<Model_list_1st_2nd> mylist;
    Z_Make_Image_Rounded makeImageRounded;

    public Adapter_Popular_Arts_Main_Activity(List<Model_list_1st_2nd> mylist) {
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View myView = inflater.inflate(R.layout.row_list_1st_2nd_main_activity, parent, false);
        makeImageRounded = new Z_Make_Image_Rounded();
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_list_1st_2nd model = mylist.get(position);
        holder.list_1st_imageView.setImageBitmap(makeImageRounded.getRoundedCornerBitmap(model.getItem_pic(), 80, 80));
        holder.list_1st_textView.setText(model.getItem_title());
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView list_1st_imageView;
        TextView list_1st_textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_1st_imageView = itemView.findViewById(R.id.list_1st_imageView);
            list_1st_textView = itemView.findViewById(R.id.list_1st_textView);
        }
    }
}
