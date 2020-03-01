package com.ArashTorDev.tablo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Z_BMP_to_String_to_BMP {

    public Bitmap stringToBitmap(String picString) {

        byte[] encodeByte = Base64.decode(picString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
    }

    public String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] itemPicByteArray = stream.toByteArray();
        return Base64.encodeToString(itemPicByteArray, Base64.DEFAULT);
    }
}
