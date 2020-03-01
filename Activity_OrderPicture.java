package com.ArashTorDev.tablo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_OrderPicture extends AppCompatActivity {

    Spinner dropdownList_size, dropdownList_frame;
    List<String> sizeList;
    ArrayAdapter<CharSequence> sizeListAdapter;
    String selectedUserSizeForCanvas = "";
    List<Model_dropdown_list_frame_row> Model_dropdown_list_frame_rows;
    Adapter_dropdownList adapter_DropdownList_frames;
    ImageView im_pic;
    Dialog dialog_chooseFromCameraOrGallery;
    CheckBox checkBox_frame;
    private final int GALLERY_CODE = 2564;
    private final int CAMERA_CODE = 6325;
    private final int STORAGE_PERMISSION_CODE = 1;
    private final int CAMERA_PERMISSION_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_picture);
        // initialize variable
        dropdownList_size = findViewById(R.id.dropdownList_size);
        sizeList = new ArrayList<>();
        dropdownList_frame = findViewById(R.id.dropdownList_frames);
        im_pic = findViewById(R.id.im_pic);
        checkBox_frame = findViewById(R.id.checkBox_frame);

        // initialize Model_dropdown_list_frame_rows method
        initialize_dropdown_list_frame_row_models();
        // initialize adapter_DropdownList_frames
        adapter_DropdownList_frames = new Adapter_dropdownList(this, Model_dropdown_list_frame_rows);
        // set the countryListAdapter to dropdownList_frame
        dropdownList_frame.setAdapter(adapter_DropdownList_frames);

        // initialize and set countryListAdapter for dropdownList_size
        sizeListAdapter = ArrayAdapter.createFromResource(this, R.array.canvas_sizes, android.R.layout.simple_spinner_item);
        sizeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownList_size.setAdapter(sizeListAdapter);

        // on click listener for size dropdown list
        dropdownList_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 5) {
                    selectedUserSizeForCanvas = adapterView.getItemAtPosition(position).toString();
                } else getSizeFromUser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkBox_frame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    dropdownList_frame.setVisibility(View.VISIBLE);
                } else dropdownList_frame.setVisibility(View.INVISIBLE);
            }
        });
    }


    public void getSizeFromUser() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Input Size");
        final EditText inputWidth = new EditText(this);
        inputWidth.setInputType(InputType.TYPE_CLASS_NUMBER);
        final TextView in = new TextView(this);
        in.setText("*");
        final EditText inputHeight = new EditText(this);
        inputHeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialog.setView(inputWidth);
        alertDialog.setView(in);
        alertDialog.setView(inputHeight);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String width = inputWidth.getText().toString();
                String height = inputHeight.getText().toString();
                selectedUserSizeForCanvas = width + "*" + height;
            }
        });
        alertDialog.setNegativeButton("Cancel", null);

    }

    public void initialize_dropdown_list_frame_row_models() {
        // in this method i have to get frame information from server and save to local database and every time software comes up check if change or not
        Model_dropdown_list_frame_rows = new ArrayList<>();
        Model_dropdown_list_frame_rows.add(new Model_dropdown_list_frame_row(R.drawable.frame1, "جنس : چوب\nقطر : 5cm\n", "3500تومان/متر"));
        Model_dropdown_list_frame_rows.add(new Model_dropdown_list_frame_row(R.drawable.frame2, "جنس : چوب\nقطر : 5cm\n", "3500تومان/متر"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_CODE || requestCode == CAMERA_CODE) {
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                }
                Bitmap b = null;
                try {
                    b = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                im_pic.setImageBitmap(b);
                im_pic.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
    }

    public void fromGallery(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentGallery, GALLERY_CODE);
            dialog_chooseFromCameraOrGallery.dismiss();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("Read external storage needed for upload a picture of art that you want to paint")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Activity_OrderPicture.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Activity_OrderPicture.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create().show();
            } else
                ActivityCompat.requestPermissions(Activity_OrderPicture.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    public void fromCamera(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCamera, CAMERA_CODE);
            dialog_chooseFromCameraOrGallery.dismiss();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("Camera permission needed for take a picture of art that you want to paint")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Activity_OrderPicture.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Activity_OrderPicture.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create().show();
            } else {
                ActivityCompat.requestPermissions(Activity_OrderPicture.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
        }

    }

    public void cancelButton(View view) {
        //back to the main activity and nothing sends
        Intent intent = new Intent(Activity_OrderPicture.this, Activity_Main.class);
        startActivity(intent);
    }

    public void okButton(View view) {
        // this button send information that user fill in this activity to the server and shows him/her a toast that is done or not
        Toast.makeText(this, "Your order is placed and we will answer you soon about price and delivery time", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Activity_OrderPicture.this, Activity_Main.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intentGallery, GALLERY_CODE);
                    dialog_chooseFromCameraOrGallery.dismiss();
                }
                break;
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCamera, CAMERA_CODE);
                    dialog_chooseFromCameraOrGallery.dismiss();
                }
                break;
        }
    }

    public void choosePic_ActivityOrderArt(View view) {
        dialog_chooseFromCameraOrGallery = new Dialog(this);
        Objects.requireNonNull(dialog_chooseFromCameraOrGallery.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_chooseFromCameraOrGallery.setContentView(R.layout.dialog_camera_or_gallery_layout);
        dialog_chooseFromCameraOrGallery.show();
    }
}
