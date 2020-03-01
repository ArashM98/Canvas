package com.ArashTorDev.tablo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.skydoves.elasticviews.ElasticButton;

public class Dialog_reset_password extends AppCompatActivity {

    EditText ed_email_reset_password;
    String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reset_password);
        //initialize variables
        ed_email_reset_password = findViewById(R.id.ed_email_reset);

        ed_email_reset_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String a = "";
                a += charSequence;
                Toast.makeText(getContext(), a, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public Context getContext() {
        return Dialog_reset_password.this;
    }

}
