package com.ArashTorDev.tablo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class Activity_SignUp_SignIn extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Dialog dialog;
    TextView ed_userArtistOrBuyer;
    Adapter_Fragment_signUp_signIn adapter;
    Dialog_reset_password dialog_reset_password;
    String userEmail = "";
    boolean verifyEmail;
    EditText ed_reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_sign_in);
        //initialize Variables
        tabLayout = findViewById(R.id.tabLayout_signUp_signIn);
        viewPager = findViewById(R.id.viewPager_signUp_signIn);
        adapter = new Adapter_Fragment_signUp_signIn(getSupportFragmentManager(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        dialog_reset_password = new Dialog_reset_password();
        ed_reset_password = findViewById(R.id.ed_email_reset);


        //present fragments to countryListAdapter
        adapter.addFragment(new Fragment_Class_Login(), "Sign In");
        adapter.addFragment(new Fragment_Class_SignUp(), "Sign Up");

        // setting the countryListAdapter to view pager
        viewPager.setAdapter(adapter);
        // setting tab layout
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    public void chooseArtistOrBuyer(View view) {
        ed_userArtistOrBuyer = (TextView) view;
        dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_choose_artist_buyer);
        dialog.show();
    }

    public void chooseArtistOrBuyerDialog(View view) {
        String tag = view.getTag().toString();
        String iAmArtist = getResources().getString(R.string.signUp_Dialog_IAMArtist);
        String iAmBuyer = getResources().getString(R.string.signUp_Dialog_IAMBuyer);
        if (tag.equals("artist")) {
            ed_userArtistOrBuyer.setText(iAmArtist);
            dialog.dismiss();
        } else if (tag.equals("buyer")) {
            ed_userArtistOrBuyer.setText(iAmBuyer);
            dialog.dismiss();
        }
    }

}
