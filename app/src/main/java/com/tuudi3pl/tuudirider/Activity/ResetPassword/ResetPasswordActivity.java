package com.tuudi3pl.tuudirider.Activity.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.PreferenceManagerLogin;

public class ResetPasswordActivity extends AppCompatActivity {

    TextInputEditText et_username,et_password;

    Button button_reset;

    PreferenceManagerLogin session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }
}
