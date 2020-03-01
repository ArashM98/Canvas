package com.ArashTorDev.tablo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UsersDatabase")
public class Model_Database_Users {

    @PrimaryKey(autoGenerate = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String name , email, bio, artistOrBuyer, phoneNumber, userImageByteArray, parseUserId;

    public String getParseUserId() {
        return parseUserId;
    }

    public void setParseUserId(String parseUserId) {
        this.parseUserId = parseUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getArtistOrBuyer() {
        return artistOrBuyer;
    }

    public void setArtistOrBuyer(String artistOrBuyer) {
        this.artistOrBuyer = artistOrBuyer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserImageByteArray() {
        return userImageByteArray;
    }

    public void setUserImageByteArray(String userImageByteArray) {
        this.userImageByteArray = userImageByteArray;
    }
}
