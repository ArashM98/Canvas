package com.ArashTorDev.tablo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skydoves.elasticviews.ElasticButton;

import java.util.List;
import java.util.Objects;

public class Adapter_StyleList_StyleActivity extends ArrayAdapter {

    View convertView;

    private List<Model_list_style_StylesActivity> models;

    public Adapter_StyleList_StyleActivity(Context context, List<Model_list_style_StylesActivity> objects) {
        super(context, R.layout.row_list_style_style_activity, objects);
        this.models = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Model_list_style_StylesActivity model = models.get(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.row_list_style_style_activity, parent, false);
            }
            holder = new ViewHolder(Objects.requireNonNull(convertView));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        this.convertView = convertView;

        holder.fill(model);

        return convertView;
    }

    public class ViewHolder {

        private ImageView itemImg;
        private TextView styleName_textView, styleInfo_textView;
        private ElasticButton btn_makeRowBigger_style_activity;
        LinearLayout row_styleActivity_main;
        Z_Make_Image_Rounded makeImageRounded;


        private ViewHolder(final View view) {
            itemImg = view.findViewById(R.id.itemImg_view);
            styleName_textView = view.findViewById(R.id.styleName_textView);
            styleInfo_textView = view.findViewById(R.id.styleInfo_textView);
            btn_makeRowBigger_style_activity = view.findViewById(R.id.btn_makeRowBigger_style_activity);
            row_styleActivity_main = view.findViewById(R.id.row_styleActivity_main);

            final int viewHeight = getContext().getResources().getDimensionPixelSize(R.dimen.row_height_styles_styleActivity);

            btn_makeRowBigger_style_activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View btnView) {
                    if (btnView.getTag().toString().equals("down")) {
                        // make the view bigger
                        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                        view.startAnimation(animation);
                        btnView.setTag("up");
                    } else if (btnView.getTag().toString().equals("up")) {
                        // make view standard size
                        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_anim);
                        view.startAnimation(animation);
                        btnView.setTag("down");
                    }
                }
            });
        }

        private void fill(Model_list_style_StylesActivity model) {

            makeImageRounded = new Z_Make_Image_Rounded();
            itemImg.setImageBitmap(makeImageRounded.getRoundedCornerBitmap(model.getItemImg(), 150, 150));
            styleName_textView.setText(model.getStyle());
            styleInfo_textView.setText(model.getStyleInfo());
        }

    }

}