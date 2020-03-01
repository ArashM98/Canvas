package com.ArashTorDev.tablo;

import android.app.Application;

import com.parse.Parse;

public class ParseServerClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("wvdD2EO9vXmnGVRfmWb9qJR7Bnz6OL6jCuhZD9iq")
                .clientKey("P15kY34KrIohBlNlyj0KbGgIKoi0TEFyUhQYz8CS")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
