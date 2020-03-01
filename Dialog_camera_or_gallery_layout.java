package com.ArashTorDev.tablo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class Dialog_camera_or_gallery_layout extends AppCompatActivity {

    private final int GALLERY_CODE = 2564;
    private final int CAMERA_CODE = 6325;
    Activity_OrderPicture Activity_OrderPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_camera_or_gallery_layout);
        Activity_OrderPicture = new Activity_OrderPicture();

    }

    public void fromGallery(View view) {
        Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentGallery, GALLERY_CODE);
        Activity_OrderPicture.dialog_chooseFromCameraOrGallery.dismiss();
    }

    public void fromCamera(View view) {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera, CAMERA_CODE);
        Activity_OrderPicture.dialog_chooseFromCameraOrGallery.dismiss();
    }
}
