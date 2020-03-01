package com.ArashTorDev.tablo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.skydoves.elasticviews.ElasticButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.media.CamcorderProfile.get;
import static com.ArashTorDev.tablo.R.string.listOfLanguages_EN;

public class Activity_Setting extends AppCompatActivity {

    List<String> languageList, countryList;
    ListView language_listView, country_listView;
    ArrayAdapter<String> countryListAdapter, languagesListAdapter;
    ElasticButton btn_selectLanguage, btn_selectCountry;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // initialized variables
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//        setLanguage();

        languageList = new ArrayList<>();
        countryList = new ArrayList<>();
        btn_selectLanguage = findViewById(R.id.btn_selectLanguage_settingActivity);
        btn_selectCountry = findViewById(R.id.btn_selectCountry_settingActivity);

        // initialize list
        initializeLists();
        String defaultLang = Locale.getDefault().getDisplayLanguage();
        Toast.makeText(this, defaultLang, Toast.LENGTH_SHORT).show();

        // initialize and setting countryListAdapter for list view
        countryListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, countryList);
        languagesListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, languageList);

    }

    private void setLanguage() {
        String selectedLanguage = preferences.getString("current_language","en");
        setLocale(selectedLanguage);
    }

    public void initializeLists() {
        // initialize list of countries
        countryList.add(getResources().getString(R.string.listOfCountries_Iran));
        countryList.add(getResources().getString(R.string.listOfCountries_Germany));
        countryList.add(getResources().getString(R.string.listOfCountries_UnitedArabEmirates));
        countryList.add(getResources().getString(R.string.listOfCountries_Netherlands));
        countryList.add(getResources().getString(R.string.listOfCountries_France));
        countryList.add(getResources().getString(R.string.listOfCountries_Turkey));

        //initialize list of languages
        languageList.add(getResources().getString(listOfLanguages_EN));
        languageList.add(getResources().getString(R.string.listOfLanguages_PER));
    }

    public void selectLanguage(View view) {
        // initialize listView for language list
        language_listView = new ListView(this);
        language_listView.setAdapter(languagesListAdapter);
        language_listView.setBackground(getResources().getDrawable(R.drawable.list_view_background_transparent));
        // create Alert dialog_chooseFromCameraOrGallery
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(language_listView, 70, 500, 70, 500);
        dialog.show();
        // set on item click listener for language listView
        language_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedLang = languageList.get(i);
                Toast.makeText(Activity_Setting.this, selectedLang, Toast.LENGTH_LONG).show();
                btn_selectLanguage.setText(selectedLang);
                switch (i) {
                    case 0:
                        setLocale("en");
                        break;
                    case 1:
                        setLocale("fa");
                        break;
                }
                dialog.dismiss();
            }
        });
    }

    public void selectCountry(View view) {
        // initialize listView for language list
        country_listView = new ListView(this);
        country_listView.setAdapter(countryListAdapter);
        country_listView.setBackground(getResources().getDrawable(R.drawable.list_view_background_transparent));
        // create Alert dialog_chooseFromCameraOrGallery
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(country_listView, 70, 500, 70, 500);
        dialog.show();
        // set on item click listener for language listView
        country_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                btn_selectCountry.setText(countryList.get(i));
                dialog.dismiss();
            }
        });
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Activity_Setting.class);
        finish();
        startActivity(refresh);
    }

    public void okButton(View view) {
        onBackPressed();
    }

}
