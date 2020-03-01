package com.ArashTorDev.tablo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

public class Activity_settings_new extends PreferenceActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);

        //initialized variables
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setLanguage();
    }

    private void setLanguage() {
        String selectedLanguage = preferences.getString("current_language", "en");
        setLocale(selectedLanguage);
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
}
