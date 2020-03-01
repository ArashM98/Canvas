package com.ArashTorDev.tablo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skydoves.elasticviews.ElasticButton;

import java.util.List;

public class Adapter_shopping_cart_list extends ArrayAdapter {

    private List<Model_shopping_cart_list> models;
    private View.OnClickListener onEliminateButtonClickListener, onItemClickListener;
    int position;


    public Adapter_shopping_cart_list(Context context, List<Model_shopping_cart_list> models) {
        super(context, R.layout.row_shopping_cart_list, models);
        this.models = models;
    }

    void setOnEliminateButtonClickListener(View.OnClickListener onEliminateButtonClickListener) {
        this.onEliminateButtonClickListener = onEliminateButtonClickListener;
    }

    void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Model_shopping_cart_list model = models.get(position);
        ViewHolder holder;
        this.position = position;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_shopping_cart_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fill(model);

        return convertView;
    }

    public class ViewHolder {
        ImageView itemPic;
        TextView tv_productName, tv_productSize, tv_productArtist, tv_productSeller, tv_productGuarantee;
        ElasticButton btn_eliminationOrder;
        Z_Make_Image_Rounded makeImageRounded;

        void fill(Model_shopping_cart_list model) {

            makeImageRounded = new Z_Make_Image_Rounded();

            itemPic.setImageBitmap(makeImageRounded.getRoundedCornerBitmap(model.getProductPic(),150,150));
            tv_productName.setText(model.getProductName());
            tv_productArtist.setText(model.getProductArtist());
            tv_productSeller.setText(model.getProductSeller());
            tv_productSize.setText(model.getProductSize());
            tv_productGuarantee.setText(model.getProductGuarantee());
        }

        ViewHolder(View itemView) {
            // setting this class and listener for the view of the row
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);

            itemPic = itemView.findViewById(R.id.im_productPic);
            tv_productName = itemView.findViewById(R.id.tv_productName);
            tv_productSize = itemView.findViewById(R.id.tv_productSize);
            tv_productArtist = itemView.findViewById(R.id.tv_productArtist);
            tv_productSeller = itemView.findViewById(R.id.tv_productSeller);
            tv_productGuarantee = itemView.findViewById(R.id.tv_productGuarantee);
            btn_eliminationOrder = itemView.findViewById(R.id.btn_eliminationOrder);

            // setting click listener for elimination button
            btn_eliminationOrder.setTag(position);
            btn_eliminationOrder.setOnClickListener(onEliminateButtonClickListener);
        }

        int getAdapterPosition() {
            return position;
        }

    }
}
