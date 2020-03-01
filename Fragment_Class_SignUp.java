package com.ArashTorDev.tablo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.skydoves.elasticviews.ElasticButton;

import java.util.Objects;

public class Fragment_Class_SignUp extends Fragment {

    View view;
    private ElasticButton okBtn, cancelBtn;
    private String name, lastName, email, phoneNumber, status, password;
    private EditText ed_name, ed_lastName, ed_Email, ed_phoneNumber, ed_password;
    private TextView ed_status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // getting the view of the fragment
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        // getting views on fragment
        ed_name = view.findViewById(R.id.ed_usersName_signUp);
        ed_password = view.findViewById(R.id.ed_usersPassword_signUp);
        ed_lastName = view.findViewById(R.id.ed_usersLastName_signUp);
        ed_phoneNumber = view.findViewById(R.id.ed_usersPhoneNumber_signUp);
        ed_Email = view.findViewById(R.id.ed_usersEmail_signUp);
        ed_status = view.findViewById(R.id.ed_usersArtistOrBuyer_SignUp);
        okBtn = view.findViewById(R.id.okBtn_signUpFragment);
        cancelBtn = view.findViewById(R.id.cancelBtn_signUpFragment);
        //setting on click listener for the buttons

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (Objects.requireNonNull(getActivity())).onBackPressed();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmptyEditTexts()) {
                    ParseUser user = new ParseUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.put("phone_number", phoneNumber);
                    user.put("status", status);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getContext(), getResources().getString(R.string.signUp_done), Toast.LENGTH_SHORT).show();
                                Objects.requireNonNull(getActivity()).onBackPressed();
                            } else {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.Fill_all_fields_signUp_fragment), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private boolean isEmptyEditTexts() {
        name = ed_name.getText().toString();
        lastName = ed_lastName.getText().toString();
        password = String.valueOf(ed_password.getText());
        email = ed_Email.getText().toString();
        phoneNumber = ed_phoneNumber.getText().toString();
        status = ed_status.getText().toString();

        if (name.isEmpty() && lastName.isEmpty() && password.isEmpty() && email.isEmpty() && phoneNumber.isEmpty() && status.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
