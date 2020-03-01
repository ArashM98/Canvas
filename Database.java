package com.ArashTorDev.tablo;

import androidx.room.RoomDatabase;


@androidx.room.Database(entities = {Model_Database_Arts.class, Model_Database_Users.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract Dao getDao();

}
