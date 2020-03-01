package com.ArashTorDev.tablo;

import android.graphics.Bitmap;

public class Model_list_style_StylesActivity {
    private String style,styleInfo;
    private Bitmap itemImg;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleInfo() {
        return styleInfo;
    }

    public void setStyleInfo(String styleInfo) {
        this.styleInfo = styleInfo;
    }

    public Bitmap getItemImg() {
        return itemImg;
    }

    public void setItemImg(Bitmap itemImg) {
        this.itemImg = itemImg;
    }

    public Model_list_style_StylesActivity(String style, String styleInfo, Bitmap itemImg) {
        this.style = style;
        this.styleInfo = styleInfo;
        this.itemImg = itemImg;
    }
}
