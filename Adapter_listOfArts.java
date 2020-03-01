package com.ArashTorDev.tablo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.skydoves.elasticviews.ElasticImageView;
import java.util.List;

public class Adapter_listOfArts extends RecyclerView.Adapter<Adapter_listOfArts.ViewHolder> {

    private List<Model_Database_Arts> models;
    private Z_BMP_to_String_to_BMP z_bmp_to_string_to_bmp;
    private Z_Make_Image_Rounded z_make_image_rounded;
    private String txt_productName, txt_productArtist, txt_productSize, txt_productSeller;
    private Context context;

    Adapter_listOfArts(List<Model_Database_Arts> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_list_of_arts_activity, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        z_bmp_to_string_to_bmp = new Z_BMP_to_String_to_BMP();
        z_make_image_rounded = new Z_Make_Image_Rounded();

        // get strings from resources
        txt_productName = context.getResources().getString(R.string.tv_productName);
        txt_productArtist = context.getResources().getString(R.string.tv_productArtist);
        txt_productSeller = context.getResources().getString(R.string.tv_productSeller);
        txt_productSize = context.getResources().getString(R.string.tv_productSize);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("adapter_listOfArts ", String.valueOf(models.size()));
        Model_Database_Arts myModel = models.get(position);
        // initialize item information from database
        txt_productName += myModel.getArtsName();
        txt_productArtist += myModel.getArtistsName();
        txt_productSeller += myModel.getParseUserName();
        txt_productSize += myModel.getSize();
        holder.tv_productName.setText(txt_productName);
        holder.tv_productArtist.setText(txt_productArtist);
        holder.tv_productSize.setText(txt_productSize);
        holder.tv_productSeller.setText(txt_productSeller);

        // change string to pic and make the corners rounded
        Bitmap itemPic = z_bmp_to_string_to_bmp.stringToBitmap(myModel.getArt());
        holder.im_productPic.setImageBitmap(z_make_image_rounded.getRoundedCornerBitmap(itemPic, 150, 150));
    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_productName, tv_productArtist, tv_productSize, tv_productSeller;
        ElasticImageView im_productPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productName = itemView.findViewById(R.id.tv_productName_ArtsList);
            tv_productArtist = itemView.findViewById(R.id.tv_productArtist_ArtsList);
            tv_productSize = itemView.findViewById(R.id.tv_productSize_ArtsList);
            tv_productSeller = itemView.findViewById(R.id.tv_productSeller_ArtsList);
            im_productPic = itemView.findViewById(R.id.im_productPic_ArtsList);

        }
    }
}
