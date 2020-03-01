package com.ArashTorDev.tablo;

import android.graphics.Bitmap;

public class Model_shopping_cart_list {

    private String productName , productSize , productArtist , productSeller , productGuarantee;
    private Bitmap productPic;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductArtist() {
        return productArtist;
    }

    public void setProductArtist(String productArtist) {
        this.productArtist = productArtist;
    }

    public String getProductSeller() {
        return productSeller;
    }

    public void setProductSeller(String productSeller) {
        this.productSeller = productSeller;
    }

    public String getProductGuarantee() {
        return productGuarantee;
    }

    public void setProductGuarantee(String productGuarantee) {
        this.productGuarantee = productGuarantee;
    }

    public Bitmap getProductPic() {
        return productPic;
    }

    public void setProductPic(Bitmap productPic) {
        this.productPic = productPic;
    }

    public Model_shopping_cart_list(String productName, String productSize, String productArtist, String productSeller, String productGuarantee, Bitmap productPic) {
        this.productName = productName;
        this.productSize = productSize;
        this.productArtist = productArtist;
        this.productSeller = productSeller;
        this.productGuarantee = productGuarantee;
        this.productPic = productPic;
    }
}
