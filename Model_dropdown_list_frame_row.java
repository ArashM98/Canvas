package com.ArashTorDev.tablo;

import android.widget.ImageView;

public class Model_dropdown_list_frame_row {

    private int frameImage;
    private String information, price;

    public Model_dropdown_list_frame_row(int frameImage, String information, String price) {
        this.frameImage = frameImage;
        this.information = information;
        this.price = price;
    }

    public int getFrameImage() {
        return frameImage;
    }

    public void setFrameImage(int frameImage) {
        this.frameImage = frameImage;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
