package com.ArashTorDev.tablo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skydoves.elasticviews.ElasticButton;

import java.util.List;

public class Adapter_ListOfUserArtWork extends RecyclerView.Adapter<Adapter_ListOfUserArtWork.ViewHolder> {

    private List<Model_Database_Arts> models;
    private Z_Make_Image_Rounded makeImageRounded;
    private Z_BMP_to_String_to_BMP z_bmp_to_string_to_bmp;
    private String txt_productName, txt_productSize, txt_productSeller, txt_productArtist;
    private Context context;

    public Adapter_ListOfUserArtWork(List<Model_Database_Arts> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_user_art_work, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        makeImageRounded = new Z_Make_Image_Rounded();
        z_bmp_to_string_to_bmp = new Z_BMP_to_String_to_BMP();

        // get strings resources from method in main activity
        txt_productName = context.getResources().getString(R.string.tv_productName);
        txt_productArtist = context.getResources().getString(R.string.tv_productArtist);
        txt_productSeller = context.getResources().getString(R.string.tv_productSeller);
        txt_productSize = context.getResources().getString(R.string.tv_productSize);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_Database_Arts myModel = models.get(position);
        //initialize holder's views
        txt_productName += myModel.getArtsName();
        txt_productArtist += myModel.getArtistsName();
        txt_productSeller += myModel.getParseUserName();
        txt_productSize += myModel.getSize();
        holder.tv_productName.setText(txt_productName);
        holder.tv_productArtist.setText(txt_productArtist);
        holder.tv_productSize.setText(txt_productSize);
        holder.tv_productSeller.setText(txt_productSeller);
        // get bitmap from string
        Bitmap itemPic = z_bmp_to_string_to_bmp.stringToBitmap(myModel.getArt());
        holder.itemPic.setImageBitmap(makeImageRounded.getRoundedCornerBitmap(itemPic, 150, 150));
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemPic;
        TextView tv_productName, tv_productSize, tv_productArtist, tv_productSeller;
        ElasticButton btn_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPic = itemView.findViewById(R.id.im_productPic_userArtWork);
            tv_productName = itemView.findViewById(R.id.tv_productName_userArtWork);
            tv_productArtist = itemView.findViewById(R.id.tv_productArtist_userArtWork);
            tv_productSeller = itemView.findViewById(R.id.tv_productSeller_userArtWork);
            tv_productSize = itemView.findViewById(R.id.tv_productSize_userArtWork);
            btn_edit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
