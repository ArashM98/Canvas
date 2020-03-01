package com.ArashTorDev.tablo;

import android.graphics.Bitmap;

public class Model_list_1st_2nd {

    Bitmap item_pic;
    String item_title;

    public Bitmap getItem_pic() {
        return item_pic;
    }

    public void setItem_pic(Bitmap item_pic) {
        this.item_pic = item_pic;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public Model_list_1st_2nd(Bitmap item_pic, String item_title) {
        this.item_pic = item_pic;
        this.item_title = item_title;
    }
}
