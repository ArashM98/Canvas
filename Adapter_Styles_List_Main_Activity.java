package com.ArashTorDev.tablo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Styles_List_Main_Activity extends RecyclerView.Adapter<Adapter_Styles_List_Main_Activity.ViewHolder> {

    private List<Model_list_1st_2nd> myList;
    private View.OnClickListener onItemClickListener;
    private Z_Make_Image_Rounded makeImageRounded;

    public Adapter_Styles_List_Main_Activity(List<Model_list_1st_2nd> myList) {
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View myView = inflater.inflate(R.layout.row_list_1st_2nd_main_activity, parent, false);
        ViewHolder holder = new ViewHolder(myView);
        makeImageRounded = new Z_Make_Image_Rounded();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_list_1st_2nd model = myList.get(position);
        makeImageRounded = new Z_Make_Image_Rounded();
        holder.list_1st_imageView.setImageBitmap(makeImageRounded.getRoundedCornerBitmap(model.getItem_pic(), 100, 100));
        holder.list_1st_textView.setText(model.getItem_title());
    }

    @Override
    public int getItemCount() {
        return myList.size();

    }

    void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView list_1st_imageView;
        TextView list_1st_textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
            list_1st_imageView = itemView.findViewById(R.id.list_1st_imageView);
            list_1st_textView = itemView.findViewById(R.id.list_1st_textView);
        }
    }
}
