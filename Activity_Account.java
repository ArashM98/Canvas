package com.ArashTorDev.tablo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Activity_Account extends AppCompatActivity {

    List<Model_Database_Arts> userArtWorkList;
    RecyclerView recyclerView_userArtWork;
    Adapter_ListOfUserArtWork adapter_recyclerView_listOfUserArtWorks;
    String currentUserId, SAVED_STATUS, username_Default, userId_default;
    String userInfo_Name, userInfo_Email, userInfo_PhoneNumber, userInfo_Bio, userInfo_Status, userInfo_profilePic;
    EditText ed_userName, ed_userEmail, ed_userPhoneNumber, ed_userStatus, ed_userBio;
    // views in dialog_AddArtToUserArtList
    EditText ed_addArt_ArtName, ed_addArt_ArtArtist;
    Spinner sp_addArtDialog_ArtSize, sp_addArtDialog_ArtStyle, sp_addArtDialog_ArtCountry;
    ArrayAdapter<String> adapter_spinner_ArtSize, adapter_spinner_ArtStyle, adapter_spinner_ArtCountry;
    ElasticImageView im_addArt_ArtPic;
    String artName, artArtist, artSize, artStyle, artCountry, artType;
    ImageView im_userPic;
    Dialog dialog_chooseFromCameraOrGallery;
    AlertDialog dialog_AddArtToUserArtList;
    ElasticButton btn_edit, btn_ok;
    private final int GALLERY_CODE = 2564;
    private final int CAMERA_CODE = 6325;
    private final int STORAGE_PERMISSION_CODE = 1;
    private final int CAMERA_PERMISSION_CODE = 2;
    ParseUser currentUserPU;
    boolean userProfilePicReq = false, userArtReq = false;
    Bitmap userArtPic;
    Database database;
    Dao dao;
    Model_Database_Users currentUserDB;
    static int im_width, im_height;
    Z_Make_Image_Rounded makeImageRounded;
    Z_BMP_to_String_to_BMP z_bmp_to_string_to_bmp;
    LayoutInflater inflater;
    String[] list_size_addArtDialog, list_style__addArtDialog, list_listOfCountries_addArtDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // initialize variables
        userArtWorkList = new ArrayList<>();
        recyclerView_userArtWork = findViewById(R.id.recyclerView_userArtwork);
        adapter_recyclerView_listOfUserArtWorks = new Adapter_ListOfUserArtWork(userArtWorkList);
        adapter_recyclerView_listOfUserArtWorks.setContext(this);
        ed_userName = findViewById(R.id.ed_userName);
        ed_userEmail = findViewById(R.id.ed_userEmail);
        ed_userPhoneNumber = findViewById(R.id.ed_userNumber);
        ed_userStatus = findViewById(R.id.ed_userStatus);
        ed_userBio = findViewById(R.id.tv_userBio);
        im_userPic = findViewById(R.id.im_userProfilePic);
        btn_edit = findViewById(R.id.btn_edit_AccountActivity);
        btn_ok = findViewById(R.id.btn_ok_ActivityAccount);
        database = Room.databaseBuilder(this, Database.class, getResources().getString(R.string.Database_Arts_className)).allowMainThreadQueries().build();
        dao = database.getDao();
        currentUserPU = new ParseUser();
        makeImageRounded = new Z_Make_Image_Rounded();
        z_bmp_to_string_to_bmp = new Z_BMP_to_String_to_BMP();
        artType = getResources().getString(R.string.artsType);
        SAVED_STATUS = getResources().getString(R.string.Saved);
        username_Default = getResources().getString(R.string.Username_Default);
        userId_default = getResources().getString(R.string.UserId_Default);
        inflater = getLayoutInflater();
        // initialize list of size and styles by language
        list_size_addArtDialog = getResources().getStringArray(R.array.canvas_sizes);
        String currentLang = Locale.getDefault().getDisplayLanguage();
        if (currentLang.equalsIgnoreCase(getResources().getString(R.string.listOfLanguages_EN))) {
            list_style__addArtDialog = getResources().getStringArray(R.array.styles_array_en_default);
            list_listOfCountries_addArtDialog = getResources().getStringArray(R.array.countries_array_EN);
        } else if (currentLang.equalsIgnoreCase(getResources().getString(R.string.listOfLanguages_PER))) {
            list_style__addArtDialog = getResources().getStringArray(R.array.styles_array_ir);
            list_listOfCountries_addArtDialog = getResources().getStringArray(R.array.countries_array_IR);
        }


        // getting user information from server or database
        currentUserPU = ParseUser.getCurrentUser();
        currentUserId = currentUserPU.getObjectId();
        gettingUserInformationFromServer();
        // create and setting layout manager for recycler view
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView_userArtWork.setLayoutManager(manager);
        recyclerView_userArtWork.setAdapter(adapter_recyclerView_listOfUserArtWorks);
    }

    public boolean initializeUserArtWorks() {

        try {
            userArtWorkList.clear();
            userArtWorkList.addAll(dao.loadAUserArts(currentUserId));
            Log.i("loadUserArtWorks : ", "loading user art works is done");
            Log.i("initializeUserArtWorks:", String.valueOf(userArtWorkList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userArtWorkList.size() > 0;
    }

    public void editUserInfo(View view) {
        String btn_editText = getResources().getString(R.string.btn_done);
        if (!btn_edit.getText().equals(btn_editText)) {
            ed_userName.setEnabled(true);
            ed_userEmail.setEnabled(true);
            ed_userPhoneNumber.setEnabled(true);
            ed_userBio.setEnabled(true);
            ed_userStatus.setEnabled(true);
            btn_edit.setText(R.string.btn_done);
            btn_ok.setEnabled(false);
        } else {
            ed_userName.setEnabled(false);
            ed_userEmail.setEnabled(false);
            ed_userPhoneNumber.setEnabled(false);
            ed_userBio.setEnabled(false);
            ed_userStatus.setEnabled(false);
            btn_edit.setText(R.string.btn_edit);
            btn_ok.setEnabled(true);
            updateUserInfo();
        }
    }

    public void updateUserInfo() {
        userInfo_Name = ed_userName.getText().toString();
        userInfo_Email = ed_userEmail.getText().toString();
        userInfo_PhoneNumber = ed_userPhoneNumber.getText().toString();
        userInfo_Bio = ed_userBio.getText().toString();
        userInfo_Status = ed_userStatus.getText().toString();

        currentUserPU.put(getResources().getString(R.string.ParseUser_Bio), userInfo_Bio);
        currentUserPU.put(getResources().getString(R.string.ParseUser_Status), userInfo_Status);
        currentUserPU.put(getResources().getString(R.string.ParseUser_PhoneNumber), userInfo_PhoneNumber);
        currentUserPU.setUsername(userInfo_Name);
        currentUserPU.put(getResources().getString(R.string.ParseUser_userPic), userInfo_profilePic);

        currentUserPU.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(Activity_Account.this, "saved", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(Activity_Account.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void okButton(View view) {
        onBackPressed();
    }

    public void logout(View view) {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(Activity_Account.this, getResources().getString(R.string.Logout_toast_Activity_Account), Toast.LENGTH_SHORT).show();
                onBackPressed();
                Intent intent = new Intent(Activity_Account.this, Activity_SignUp_SignIn.class);
                startActivity(intent);
            }
        });
    }

    public void gettingUserInformationFromServer() {

        if (currentUserPU != null) {

            //changing user pic from string to bitmap
            String userPicStr = null;
            if (currentUserPU.get(getResources().getString(R.string.ParseUser_userPic)) != null) {
                userPicStr = Objects.requireNonNull(currentUserPU.get(getResources().getString(R.string.ParseUser_userPic))).toString();
            } else {
                try {
                    currentUserDB = dao.loadUserById(currentUserId);
                    userPicStr = currentUserDB.getUserImageByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (userPicStr != null) {
                Bitmap userPicBMP = z_bmp_to_string_to_bmp.stringToBitmap(userPicStr);
                Bitmap b = makeImageRounded.getRoundedCornerBitmap(userPicBMP, 150, 150);
                im_userPic.setImageBitmap(b);
            }

            //getting user info
            try {
                ed_userName.setText(currentUserPU.getUsername());
                ed_userEmail.setText(currentUserPU.getEmail());
                ed_userBio.setText(currentUserPU.getString(getResources().getString(R.string.ParseUser_Bio)));
                ed_userPhoneNumber.setText(currentUserPU.getString(getResources().getString(R.string.ParseUser_PhoneNumber)));
                ed_userStatus.setText(currentUserPU.getString(getResources().getString(R.string.ParseUser_Status)));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            // fill the users Art list
            if (initializeUserArtWorks()) {
                adapter_recyclerView_listOfUserArtWorks.notifyDataSetChanged();
                Log.i("get user information : ", "load from database");
            } else {
                String productName = getResources().getString(R.string.tv_NoName);
                String productArtist = getResources().getString(R.string.shoppingCartList_defineArtist) + getResources().getString(R.string.tv_NoArtists);
                String productSize = getResources().getString(R.string.tv_NoSize);
                String productSeller = getResources().getString(R.string.shoppingCartList_defineSeller) + getResources().getString(R.string.tv_NoSeller);
                String productCountry = getResources().getString(R.string.tv_NoCountry);
                Bitmap productPic = BitmapFactory.decodeResource(getResources(), R.drawable.shopping_cart_no_image);
                // fill the list with no item available
                Model_Database_Arts model = new Model_Database_Arts();
                model.setArtsName(productName);
                model.setSize(productSize);
                model.setParseUserName(productSeller);
                model.setArtistsName(productArtist);
                model.setCountry(productCountry);
                model.setArt(z_bmp_to_string_to_bmp.bitmapToString(productPic));
                model.setStyle(artStyle);
                model.setParseUserName(username_Default);
                model.setParseUserId(userId_default);
                userArtWorkList.add(model);
                userArtWorkList.add(model);
                userArtWorkList.add(model);
                adapter_recyclerView_listOfUserArtWorks.notifyDataSetChanged();
                Log.i("get user information : ", "load from default");
            }

        } else Toast.makeText(this, "user not logged in", Toast.LENGTH_SHORT).show();
    }

    public void choosePicMethod() {
        // it will open a dialog and give us 2 choices to choose the pic from camera or gallery
        String btn_editText = getResources().getString(R.string.btn_done);
        if (btn_edit.getText().equals(btn_editText) || userArtReq) {
            dialog_chooseFromCameraOrGallery = new Dialog(this);
            Objects.requireNonNull(dialog_chooseFromCameraOrGallery.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog_chooseFromCameraOrGallery.setContentView(R.layout.dialog_camera_or_gallery_layout);
            dialog_chooseFromCameraOrGallery.show();
        }
    }

    public void chooseUserProfilePic(View view) {
        //calling choose pic method
        userArtReq = false;
        userProfilePicReq = true;
        choosePicMethod();
    }

    public void chooseArtPic(View view) {
        userArtReq = true;
        userProfilePicReq = false;
        choosePicMethod();
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
                                ActivityCompat.requestPermissions(Activity_Account.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Activity_Account.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create().show();
            } else
                ActivityCompat.requestPermissions(Activity_Account.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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
                                ActivityCompat.requestPermissions(Activity_Account.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Activity_Account.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create().show();
            } else {
                ActivityCompat.requestPermissions(Activity_Account.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
        }

    }

    public void addUserArt(View view) {
        userArtReq = true;
        userProfilePicReq = false;
        // creating a dialog for add a art to the users Art
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layout = inflater.inflate(R.layout.dialog_add_art_user_account, null);
        builder.setView(layout);
        dialog_AddArtToUserArtList = builder.create();
        Objects.requireNonNull(dialog_AddArtToUserArtList.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_AddArtToUserArtList.show();
        dialog_AddArtToUserArtList.getWindow().setLayout(800, 800);

        im_addArt_ArtPic = layout.findViewById(R.id.im_addArt_ArtPic);
        ed_addArt_ArtName = layout.findViewById(R.id.ed_addArt_ArtsName);
        ed_addArt_ArtArtist = layout.findViewById(R.id.ed_addArt_ArtArtist);
        sp_addArtDialog_ArtSize = layout.findViewById(R.id.sp_addArtDialog_ArtsSize);
        sp_addArtDialog_ArtStyle = layout.findViewById(R.id.sp_addArtDialog_ArtsStyle);
        sp_addArtDialog_ArtCountry = layout.findViewById(R.id.sp_addArtDialgo_ArtsCountry);

        // setting adapter for spinners
        adapter_spinner_ArtSize = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_size_addArtDialog);
        adapter_spinner_ArtSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_addArtDialog_ArtSize.setAdapter(adapter_spinner_ArtSize);

        adapter_spinner_ArtStyle = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_style__addArtDialog);
        adapter_spinner_ArtStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_addArtDialog_ArtStyle.setAdapter(adapter_spinner_ArtStyle);

        adapter_spinner_ArtCountry = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_listOfCountries_addArtDialog);
        adapter_spinner_ArtCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_addArtDialog_ArtCountry.setAdapter(adapter_spinner_ArtCountry);
    }

    public void okCancelBtn_addArt_AccountActivity(View view) {
        if (view.getId() == R.id.okBtn_addArt) {
            initializeAddArtDialogEditTexts();
            // here will send the art item to the server
            ParseObject object = new ParseObject(getResources().getString(R.string.Database_Server_Arts_className));
            object.put(getResources().getString(R.string.ParseObject_Art_artPic), z_bmp_to_string_to_bmp.bitmapToString(userArtPic));
            object.put(getResources().getString(R.string.ParseObject_Art_Name), artName);
            object.put(getResources().getString(R.string.ParseObject_Art_Artist), artArtist);
            object.put(getResources().getString(R.string.ParseObject_Art_UserId), currentUserId);
            object.put(getResources().getString(R.string.ParseObject_Art_Username), currentUserPU.getUsername());
            object.put(getResources().getString(R.string.ParseObject_Art_Size), artSize);
            object.put(getResources().getString(R.string.ParseObject_Art_Country), artCountry);
            object.put(getResources().getString(R.string.ParseObject_Art_Type), artType);
            // save in background
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(Activity_Account.this, SAVED_STATUS, Toast.LENGTH_SHORT).show();
                        saveInDatabaseAfterSaveInServer();
                        dialog_AddArtToUserArtList.dismiss();

                    } else {
                        Toast.makeText(Activity_Account.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (view.getId() == R.id.cancelBtn_addArt) {
            dialog_AddArtToUserArtList.dismiss();
        }
    }

    public void saveInDatabaseAfterSaveInServer() {
        // here we will get the object that we set into server database
        List<ParseObject> objects = new ArrayList<>();
        ParseObject object = new ParseObject(getResources().getString(R.string.Database_Server_Arts_className));
        ParseQuery<ParseObject> query = ParseQuery.getQuery(getResources().getString(R.string.Database_Server_Arts_className));
        query.whereEqualTo(getResources().getString(R.string.ParseObject_Art_UserId), currentUserId);
        query.whereEqualTo(getResources().getString(R.string.ParseObject_Art_Name), artName);
        query.whereEqualTo(getResources().getString(R.string.ParseObject_Art_Size), artSize);
        try {
            objects = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (objects.size() > 0) {
            object = objects.get(0);
        }
        // here will send the art into database
        Model_Database_Arts model = new Model_Database_Arts();
        model.setArt(object.getString(getResources().getString(R.string.ParseObject_Art_artPic)));
        model.setArtsName(object.getString(getResources().getString(R.string.ParseObject_Art_Name)));
        model.setArtistsName(object.getString(getResources().getString(R.string.ParseObject_Art_Artist)));
        model.setParseUserName(object.getString(getResources().getString(R.string.ParseObject_Art_Username)));
        model.setSize(object.getString(getResources().getString(R.string.ParseObject_Art_Size)));
        model.setCountry(object.getString(getResources().getString(R.string.ParseObject_Art_Country)));
        model.setParseUserId(object.getString(getResources().getString(R.string.ParseObject_Art_UserId)));
        model.setParseObjectArtId(object.getObjectId());
        model.setArtsType(object.getString(getResources().getString(R.string.ParseObject_Art_Type)));
        //set model into database
        dao.insertArtItem(model);
        gettingUserInformationFromServer();
    }

    public void initializeAddArtDialogEditTexts() {
        // get texts from edit texts
        artName = ed_addArt_ArtName.getText().toString();
        artArtist = ed_addArt_ArtArtist.getText().toString();
        artSize = String.valueOf(sp_addArtDialog_ArtSize.getSelectedItem());
        artStyle = String.valueOf(sp_addArtDialog_ArtStyle.getSelectedItem());
        artCountry = String.valueOf(sp_addArtDialog_ArtCountry.getSelectedItem());

        sp_addArtDialog_ArtSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                artSize = String.valueOf(adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_addArtDialog_ArtStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                artStyle = String.valueOf(adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_addArtDialog_ArtCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                artCountry = String.valueOf(adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                if (userProfilePicReq) {
                    userInfo_profilePic = z_bmp_to_string_to_bmp.bitmapToString(b);
                    im_userPic.setImageBitmap(b);
                    userProfilePicReq = false;
                } else if (userArtReq) {
                    im_height = im_addArt_ArtPic.getHeight();
                    im_width = im_addArt_ArtPic.getWidth();
                    userArtPic = makeImageRounded.getRoundedCornerBitmap(b, im_width, im_height);
                    im_addArt_ArtPic.setImageBitmap(userArtPic);
                    userArtReq = false;
                }
            }
        }
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


}
