package com.ArashTorDev.tablo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.skydoves.elasticviews.ElasticButton;

import java.util.Objects;

public class Fragment_Class_Login extends Fragment {

    private EditText ed_Email_Number, ed_password;
    private ElasticButton okBtn, cancelBtn;
    private TextView tvForgetPassword;
    View view;
    private static AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ed_Email_Number = view.findViewById(R.id.ed_Email_Number_login);
        ed_password = view.findViewById(R.id.ed_password_login);
        okBtn = view.findViewById(R.id.okBtn_signInFragment);
        cancelBtn = view.findViewById(R.id.cancelBtn_signInFragment);
        tvForgetPassword = view.findViewById(R.id.tv_ForgetPassword);

        //set on click listeners
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmailNumber = ed_Email_Number.getText().toString();
                String userPassword = String.valueOf(ed_password.getText());
                ParseUser.logInInBackground(userEmailNumber, userPassword, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null) {
                            // get information of this user from server
                            String welcome = String.format("Welcome %s", ParseUser.getCurrentUser().getUsername());
                            Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
                            Objects.requireNonNull(getActivity()).onBackPressed();
                        } else {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View v = inflater.inflate(R.layout.dialog_reset_password, null);
                builder.setView(v);
                dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                final EditText ed_reset_password = v.findViewById(R.id.ed_email_reset);
                ElasticButton btnOK = v.findViewById(R.id.btnOk_resetPasswordDialogActivity);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = ed_reset_password.getText().toString();
                        if (!userEmail.isEmpty()) {
                            ParseUser.requestPasswordResetInBackground(userEmail, new RequestPasswordResetCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(getContext(), getResources().getString(R.string.Email_password_reset_Toast), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.Email_password_reset_correct_email), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }

}
