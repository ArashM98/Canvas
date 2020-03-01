package com.ArashTorDev.tablo;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    // set methods for arts
    @Insert
    void insertArtItem(Model_Database_Arts m);
    @Insert
    void insertAllArtItems(Model_Database_Arts... artsList);
    @Update
    void updateArtItem(Model_Database_Arts m);
    @Delete
    void deleteArtItem(Model_Database_Arts m);
    @Query("select * FROM ArtsDatabase")
    List<Model_Database_Arts>loadAllArts();
    @Query("select * FROM ArtsDatabase WHERE ParseUserId LIKE :userId")
    List<Model_Database_Arts> loadAUserArts(String userId);

    //set methods for users
    @Insert
    void insertUser(Model_Database_Users m);
    @Update
    void updateUser(Model_Database_Users m);
    @Delete
    void deleteUser(Model_Database_Users m);
    @Query("select * FROM UsersDatabase")
    List<Model_Database_Users>loadAllUsers();
    @Query("select * FROM UsersDatabase WHERE parseUserId LIKE :userId")
    Model_Database_Users loadUserById(String userId);

}
