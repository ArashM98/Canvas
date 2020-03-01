package com.ArashTorDev.tablo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Activity_filters extends AppCompatActivity {

    ListView listView_filters;
    List<String> listDataFilters;
    ArrayAdapter adapter;
    boolean[] selectedCountriesBooleanArray, selectedStylesBooleanArray, selectedArtistsBooleanArray, selectedSizesBooleanArray;
    String[] countriesArray, stylesArray, artistsArray, sizesArray;
    Bundle extras;
    Database database;
    Dao dao;

    //request code for filter activity
    private final int KEY_REQUEST_CODE_FILTERS = 351;

    // keys for saving data in saved instance state variable
    private final String KEY_SELECTED_COUNTRIES_BOOLEAN_ARRAY = "KEY_SELECTED_COUNTRIES_BOOLEAN_ARRAY";
    private final String KEY_SELECTED_STYLES_BOOLEAN_ARRAY = "KEY_SELECTED_STYLES_BOOLEAN_ARRAY";
    private final String KEY_SELECTED_ARTISTS_BOOLEAN_ARRAY = "KEY_SELECTED_ARTISTS_BOOLEAN_ARRAY";
    private final String KEY_SELECTED_SIZES_BOOLEAN_ARRAY = "KEY_SELECTED_SIZES_BOOLEAN_ARRAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        //initialize variables
        database = Room.databaseBuilder(this, Database.class, getResources().getString(R.string.Database_Arts_className)).allowMainThreadQueries().build();
        dao = database.getDao();
        listView_filters = findViewById(R.id.listView_filters_dialogBox);
        listDataFilters = new ArrayList<>();
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")) {
            countriesArray = getResources().getStringArray(R.array.countries_array_EN);
        } else if (Locale.getDefault().getLanguage().equalsIgnoreCase("fa")) {
            countriesArray = getResources().getStringArray(R.array.countries_array_IR);
        }
        stylesArray = getResources().getStringArray(R.array.styles_array_en_default);
        getUsersFromDatabase();
        sizesArray = getResources().getStringArray(R.array.canvas_sizes_for_filters);

        // retrieving data from savedInstanceState
        // get intent's extras
        extras = getIntent().getExtras();
        retrieving_savedInstanceState(extras);
        // initialize list of filters
        initializeListOfFilters();
        listView_filters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        selectCountry();
                        break;
                    case 1:
                        selectStyle();
                        break;
                    case 2:
                        selectArtist();
                        break;
                    case 3:
                        selectSize();
                }
            }
        });
    }

    public void getUsersFromDatabase() {
        List<Model_Database_Users> users = dao.loadAllUsers();
        artistsArray = new String[users.size()];
        Model_Database_Users user;
        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            artistsArray[i] = user.getName();
        }
    }

    public void retrieving_savedInstanceState(Bundle extras) {
        // this method will check the boolean information and set them to local variables

        Log.i("Retrieve method", "we re in retrieve method");

        if (extras != null && !extras.isEmpty()) {
            selectedCountriesBooleanArray = extras.getBooleanArray(KEY_SELECTED_COUNTRIES_BOOLEAN_ARRAY);
            selectedStylesBooleanArray = extras.getBooleanArray(KEY_SELECTED_STYLES_BOOLEAN_ARRAY);
            selectedArtistsBooleanArray = extras.getBooleanArray(KEY_SELECTED_ARTISTS_BOOLEAN_ARRAY);
            selectedSizesBooleanArray = extras.getBooleanArray(KEY_SELECTED_SIZES_BOOLEAN_ARRAY);
            Log.i("request code : ", Objects.requireNonNull(String.valueOf(extras.getInt("REQUEST_CODE"))));
            Log.i("Retrieve method", "boolean array got from intent");
        }
    }

    public void initializeListOfFilters() {
        listDataFilters.add(getResources().getString(R.string.filters_activity_listFiltersItem_Country));
        listDataFilters.add(getResources().getString(R.string.filters_activity_listFiltersItem_Styles));
        listDataFilters.add(getResources().getString(R.string.filters_activity_listFiltersItem_Artists));
        listDataFilters.add(getResources().getString(R.string.filters_activity_listFiltersItem_Size));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listDataFilters);

        listView_filters.setAdapter(adapter);
    }

    public void useFilters(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        // this method will send information about selected filters
        Intent intent = new Intent();
        intent.putExtra(KEY_SELECTED_COUNTRIES_BOOLEAN_ARRAY, selectedCountriesBooleanArray);
        intent.putExtra(KEY_SELECTED_ARTISTS_BOOLEAN_ARRAY, selectedArtistsBooleanArray);
        intent.putExtra(KEY_SELECTED_STYLES_BOOLEAN_ARRAY, selectedStylesBooleanArray);
        intent.putExtra(KEY_SELECTED_SIZES_BOOLEAN_ARRAY, selectedSizesBooleanArray);
        intent.putExtra("REQUEST_CODE", KEY_REQUEST_CODE_FILTERS);
        setResult(KEY_REQUEST_CODE_FILTERS, intent);
        super.onBackPressed();
    }

    public void selectCountry() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setMultiChoiceItems(countriesArray, selectedCountriesBooleanArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean b) {
                selectedCountriesBooleanArray[position] = b;
                Toast.makeText(Activity_filters.this, "position : " + position + " = " + b, Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setPositiveButton(R.string.btn_ok, null);
        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_gray_backgrounds));
        alertDialog.show();
    }

    public void selectStyle() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMultiChoiceItems(stylesArray, selectedStylesBooleanArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean b) {
                selectedStylesBooleanArray[position] = b;
            }
        });
        dialog.setPositiveButton(R.string.btn_ok, null);
        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_gray_backgrounds));
        alertDialog.show();
    }

    public void selectArtist() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMultiChoiceItems(artistsArray, selectedArtistsBooleanArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean b) {
                selectedArtistsBooleanArray[position] = b;
            }
        });
        dialog.setPositiveButton(R.string.btn_ok, null);
        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_gray_backgrounds));
        alertDialog.show();
    }

    public void selectSize() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMultiChoiceItems(sizesArray, selectedSizesBooleanArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean b) {
                selectedSizesBooleanArray[position] = b;
            }
        });
        dialog.setPositiveButton(R.string.btn_ok, null);
        dialog.setCancelable(false);
        AlertDialog alertDialog = dialog.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_gray_backgrounds));
        alertDialog.show();
    }
}
