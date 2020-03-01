package com.ArashTorDev.tablo;

import android.graphics.Bitmap;

public class Model_list_artists_profile {

    private String ArtistName, ArtistLastName;
    private String ArtistMethod;
    private Float ArtistRating;
    private Bitmap ArtistProfilePic;

    public Model_list_artists_profile(Bitmap artistProfilePic, String artistName, String artistLastName, String artistMethod, Float artistRating) {
        ArtistName = artistName;
        ArtistLastName = artistLastName;
        ArtistMethod = artistMethod;
        ArtistRating = artistRating;
        ArtistProfilePic = artistProfilePic;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public Bitmap getArtistProfilePic() {
        return ArtistProfilePic;
    }

    public void setArtistProfilePic(Bitmap artistProfilePic) {
        ArtistProfilePic = artistProfilePic;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public String getArtistLastName() {
        return ArtistLastName;
    }

    public void setArtistLastName(String artistLastName) {
        ArtistLastName = artistLastName;
    }

    public String getArtistMethod() {
        return ArtistMethod;
    }

    public void setArtistMethod(String artistMethod) {
        ArtistMethod = artistMethod;
    }

    public Float getArtistRating() {
        return ArtistRating;
    }

    public void setArtistRating(Float artistRating) {
        ArtistRating = artistRating;
    }
}
