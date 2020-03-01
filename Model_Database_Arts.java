package com.ArashTorDev.tablo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ArtsDatabase")
public class Model_Database_Arts {

    @PrimaryKey(autoGenerate = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String art;
    private String country;
    private String style;
    private String ArtistsName;
    private String size;
    private String ArtsName;
    private String ParseObjectArtId;
    private String ParseUserId;
    private String ParseUserName;
    private String artsType;
//    private String artInfo;
//
//    public String getArtInfo() {
//        return artInfo;
//    }
//
//    public void setArtInfo(String artInfo) {
//        this.artInfo = artInfo;
//    }

    public String getArtsType() {
        return artsType;
    }

    public void setArtsType(String artsType) {
        this.artsType = artsType;
    }

    public String getParseUserName() {
        return ParseUserName;
    }

    public void setParseUserName(String parseUserName) {
        ParseUserName = parseUserName;
    }

    public String getParseUserId() {
        return ParseUserId;
    }

    public void setParseUserId(String parseUserId) {
        ParseUserId = parseUserId;
    }

    public String getParseObjectArtId() {
        return ParseObjectArtId;
    }

    public void setParseObjectArtId(String parseObjectArtId) {
        ParseObjectArtId = parseObjectArtId;
    }

    public String getArtsName() {
        return ArtsName;
    }

    public void setArtsName(String artsName) {
        ArtsName = artsName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getArtistsName() {
        return ArtistsName;
    }

    public void setArtistsName(String artistsName) {
        ArtistsName = artistsName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

}
