package com.ArashTorDev.tablo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_ListOfArts extends AppCompatActivity {
    EditText ed_search;
    List<Model_Database_Arts> modelsDatabaseArts_list;
    List<Model_Database_Arts> modelsDatabaseArts_list_query;
    List<Model_Database_Users> users;
    String[] artistsArray;
    Activity_Main activity_main;
    RecyclerView recyclerView_listOfArts;
    Adapter_listOfArts adapter_listOfArts;
    Database database;
    Dao dao;
    Bundle extras;

    //request code for filter activity
    private final int KEY_REQUEST_CODE_FILTERS = 351;

    // keys for saving data in saved instance state variable
    private final String KEY_SELECTED_COUNTRIES_BOOLEAN_ARRAY = "KEY_SELECTED_COUNTRIES_BOOLEAN_ARRAY";
    private final String KEY_SELECTED_STYLES_BOOLEAN_ARRAY = "KEY_SELECTED_STYLES_BOOLEAN_ARRAY";
    private final String KEY_SELECTED_ARTISTS_BOOLEAN_ARRAY = "KEY_SELECTED_ARTISTS_BOOLEAN_ARRAY";
    private final String KEY_SELECTED_SIZES_BOOLEAN_ARRAY = "KEY_SELECTED_SIZES_BOOLEAN_ARRAY";

    boolean[] selectedCountriesBooleanArray, selectedStylesBooleanArray, selectedArtistsBooleanArray, selectedSizesBooleanArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_arts);

        // initialize variables
        ed_search = findViewById(R.id.ed_search_Activity_ListOfArts);
        activity_main = new Activity_Main();
        modelsDatabaseArts_list = new ArrayList<>();
        modelsDatabaseArts_list_query = new ArrayList<>();
        adapter_listOfArts = new Adapter_listOfArts(modelsDatabaseArts_list);
        adapter_listOfArts.setContext(this);
        database = Room.databaseBuilder(this, Database.class, getResources().getString(R.string.Database_Arts_className)).allowMainThreadQueries().build();
        dao = database.getDao();
        recyclerView_listOfArts = findViewById(R.id.listOfArts_recyclerView);
        users = new ArrayList<>();
        getUsersFromDatabase();
        retrieving_savedInstanceState(getIntent());
        initializedArtsList();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        recyclerView_listOfArts.setLayoutManager(manager);
        recyclerView_listOfArts.setAdapter(adapter_listOfArts);


        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // here searching every word that user typed
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void initializedArtsList() {
        try {
            modelsDatabaseArts_list.clear();
            modelsDatabaseArts_list.addAll(dao.loadAllArts());
            adapter_listOfArts.notifyDataSetChanged();
            Log.i("Act_listOFArts_ArtsDatabaseCount : ", String.valueOf(modelsDatabaseArts_list.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retrieving_savedInstanceState(Intent intent) {
        // this method will check the boolean information and set them to local variables
        extras = intent.getExtras();
        if (!Objects.requireNonNull(extras).isEmpty() && extras.getInt("REQUEST_CODE") == KEY_REQUEST_CODE_FILTERS) {
            selectedCountriesBooleanArray = extras.getBooleanArray(KEY_SELECTED_COUNTRIES_BOOLEAN_ARRAY);
            selectedStylesBooleanArray = extras.getBooleanArray(KEY_SELECTED_STYLES_BOOLEAN_ARRAY);
            selectedArtistsBooleanArray = extras.getBooleanArray(KEY_SELECTED_ARTISTS_BOOLEAN_ARRAY);
            selectedSizesBooleanArray = extras.getBooleanArray(KEY_SELECTED_SIZES_BOOLEAN_ARRAY);
            Log.i("retrieving extras : ", "boolean array got from intent");
        } else {
            selectedCountriesBooleanArray = new boolean[getResources().getStringArray(R.array.countries_array_EN).length];
            selectedStylesBooleanArray = new boolean[getResources().getStringArray(R.array.styles_array_en_default).length];
            selectedArtistsBooleanArray = new boolean[artistsArray.length];
            selectedSizesBooleanArray = new boolean[getResources().getStringArray(R.array.countries_array_EN).length];
            Log.i("retrieving extras : ", "boolean array initialized from base");
        }
    }

    public void getUsersFromDatabase() {
        users = dao.loadAllUsers();
        artistsArray = new String[users.size()];
        Model_Database_Users user;
        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            artistsArray[i] = user.getName();
        }
    }

    public void searchBTN(View view) {
        // this method will search for the word that user write in to the edit text
        String temp = ed_search.getText().toString();
    }


    public void selectFilters(View view) {
        // going to select filter page
        Intent intent = new Intent(Activity_ListOfArts.this, Activity_filters.class);
        intent.putExtra(KEY_SELECTED_COUNTRIES_BOOLEAN_ARRAY, selectedCountriesBooleanArray);
        intent.putExtra(KEY_SELECTED_ARTISTS_BOOLEAN_ARRAY, selectedArtistsBooleanArray);
        intent.putExtra(KEY_SELECTED_STYLES_BOOLEAN_ARRAY, selectedStylesBooleanArray);
        intent.putExtra(KEY_SELECTED_SIZES_BOOLEAN_ARRAY, selectedSizesBooleanArray);
        intent.putExtra("REQUEST_CODE", KEY_REQUEST_CODE_FILTERS);
        startActivityForResult(intent, KEY_REQUEST_CODE_FILTERS);
    }


    public void queries(String[] countries, String[] styles, String[] artists, String[] sizes) {
        for (Model_Database_Arts model : modelsDatabaseArts_list) {

            // country search
            for (String country : countries) {
                if (model.getCountry().equalsIgnoreCase(country) && duplicate(model)) {
                    modelsDatabaseArts_list_query.add(model);

                }
            }

            sizeSearch:
            for (String size : sizes) {
                try {
                    if (sizes[0].equals("All Sizes")) {
                        break sizeSearch;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (model.getSize().equals(size) && duplicate(model)) {
                    modelsDatabaseArts_list_query.add(model);
                }
            }

            // styles search
            for (String style : styles) {
                if (model.getArtsType().equals(style) && duplicate(model)) {
                    modelsDatabaseArts_list_query.add(model);

                }
            }

            // artist search
            for (String artist : artists) {
                if (model.getArtistsName().equals(artist) && duplicate(model)) {
                    modelsDatabaseArts_list_query.add(model);
                }
            }
        }

        // notify recycler that information is changed !!
        adapter_listOfArts.notifyDataSetChanged();
    }

    public boolean duplicate(Model_Database_Arts model) {

        // this method will check the model is in list or not . it will stop duplicate models in list

        boolean result = false;
        for (Model_Database_Arts model1 : modelsDatabaseArts_list_query) {
            if (model1.getParseObjectArtId().equals(model.getParseObjectArtId())) {
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KEY_REQUEST_CODE_FILTERS) {
            retrieving_savedInstanceState(Objects.requireNonNull(data));
        }
    }
}
