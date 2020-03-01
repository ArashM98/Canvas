package com.ArashTorDev.tablo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.room.Room;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service_getArtsDatabase extends Service {

    Database database;
    Dao dao;
    Bundle extras;
    String userArtPic, artName, artArtist, currentUserName, artSize, artCountry, currentUserId, artUsername, artUserId;
    ParseUser currentUserPU;
    Model_Database_Arts model_database_arts;
    String classDatabaseName, artsType;
    List<ParseObject> parseObjectList;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("service_download_DB", "service created");
        database = Room.databaseBuilder(this, Database.class, "ArtsDatabase").allowMainThreadQueries().build();
        dao = database.getDao();
        currentUserPU = new ParseUser();
        currentUserPU = ParseUser.getCurrentUser();
        if (currentUserPU != null) {
            currentUserId = currentUserPU.getObjectId();
            currentUserName = currentUserPU.getUsername();
        }
        model_database_arts = new Model_Database_Arts();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // here we download Arts database from server to local database
        Log.i("service_download_DB", "service on start command");
        extras = intent.getExtras();
        if (extras != null) {
            classDatabaseName = extras.getString("databaseClassName");
            artsType = extras.getString("ArtsType");
        }
        parseObjectList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(classDatabaseName);
        query.whereEqualTo("Type", artsType);
        try {
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        parseObjectList = objects;
                    } else {
                        Log.i("find in server : ", Objects.requireNonNull(e.getMessage()));
                    }
                }
            });
        } catch (Exception e) {
            Log.i("Parse_Exception", Objects.requireNonNull(e.getMessage()));
        }

        if (parseObjectList != null) {
            for (ParseObject object : parseObjectList) {

                Log.i("serverArtsDatabase ", String.valueOf(parseObjectList.size()));

                userArtPic = object.getString(getResources().getString(R.string.ParseObject_Art_artPic));
                artName = object.getString(getResources().getString(R.string.ParseObject_Art_Name));
                artArtist = object.getString(getResources().getString(R.string.ParseObject_Art_Artist));
                artUsername = object.getString(getResources().getString(R.string.ParseObject_Art_Username));
                artSize = object.getString(getResources().getString(R.string.ParseObject_Art_Size));
                artCountry = object.getString(getResources().getString(R.string.ParseObject_Art_Country));
                artUserId = object.getString(getResources().getString(R.string.ParseObject_Art_UserId));
                artsType = object.getString(getResources().getString(R.string.ParseObject_Art_Type));

                model_database_arts.setArt(userArtPic);
                model_database_arts.setArtsName(artName);
                model_database_arts.setArtsName(artArtist);
                model_database_arts.setParseUserName(artUsername);
                model_database_arts.setSize(artSize);
                model_database_arts.setCountry(artCountry);
                model_database_arts.setParseUserId(artUserId);
                model_database_arts.setArtsType(artsType);
                model_database_arts.setParseObjectArtId(object.getObjectId());

                dao.insertArtItem(model_database_arts);
                Log.i("insert Art ", artName);
            }
            Log.i("service_downloadArts ", "Downloading Done");
            List<Model_Database_Arts> models = dao.loadAllArts();
            Log.i("ArtDatabase", String.valueOf(models.size()));
        }
        loadUserInformation();
        return super.onStartCommand(intent, flags, startId);
    }

    private void loadUserInformation() {
        ParseUser userPU = ParseUser.getCurrentUser();
        if (userPU != null) {
            Model_Database_Users userDB = new Model_Database_Users();
            userDB.setParseUserId(userPU.getObjectId());
            userDB.setUserImageByteArray(userPU.getString(getResources().getString(R.string.ParseObject_Art_artPic)));
            userDB.setArtistOrBuyer(userPU.getString(getResources().getString(R.string.ParseObject_Art_Type)));
            userDB.setBio(userPU.getString(getResources().getString(R.string.ParseUser_Bio)));
            userDB.setEmail(userPU.getEmail());
            userDB.setName(userPU.getUsername());
            userDB.setPhoneNumber(getResources().getString(R.string.ParseUser_PhoneNumber));

            dao.insertUser(userDB);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
