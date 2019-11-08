package com.tuudi3pl.tuudirider.Activity.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.tuudi3pl.tuudirider.Activity.Dashboard.MainDashboard;
import com.tuudi3pl.tuudirider.Activity.ResetPassword.ResetPasswordActivity;
import com.tuudi3pl.tuudirider.Connection.URL;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.PreferenceManagerLogin;
import com.tuudi3pl.tuudirider.Utils.StandardProgressDialog;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{

    TextView textView_welcome1,textView_welcome2,textView_forgetPassword,textView_signUp;

    TextInputEditText et_username,et_password;

    Button button_login;

    private static long back_pressed;

    StandardProgressDialog standardProgressDialog;

    PreferenceManagerLogin session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        //FOR RESTRICT HTTPS
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        session = new PreferenceManagerLogin(getApplicationContext());

        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        declareAndCustomFont();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonLogin();
            }
        });

        textView_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent next = new Intent(getApplicationContext(), ResetPasswordActivity.class);
//                startActivity(next);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())  moveTaskToBack(true);
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    private void declareAndCustomFont() {
        textView_welcome1 = findViewById(R.id.textView_welcome1);
        textView_welcome2 = findViewById(R.id.textView_welcome2);
        textView_forgetPassword = findViewById(R.id.textView_forgetPassword);
        textView_signUp = findViewById(R.id.textView_signUp);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        button_login = findViewById(R.id.button_login);

        //SET FONT CUSTOM
        TypeFaceClass.setTypeFaceTextView(textView_welcome1,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_welcome2,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_forgetPassword,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_signUp,getApplicationContext());
        TypeFaceClass.setTypeFaceTextInputEditText(et_username,getApplicationContext());
        TypeFaceClass.setTypeFaceTextInputEditText(et_password,getApplicationContext());
        TypeFaceClass.setTypeFaceButton(button_login,getApplicationContext());
    }

    private void onClickButtonLogin(){
        if(et_username.getText().toString().equals("")){
            et_username.setError("Please fill email");
        }else if(et_password.getText().toString().equals("")){
            et_password.setError("Please fill password");
        }else{
            standardProgressDialog.show();
            login();
        }
    }

    private void login(){
        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                URL.URL_LOGIN+"username="+et_username.getText().toString()+"&password="+et_password.getText().toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        standardProgressDialog.dismiss();
                        Log.d("yaw",jsonObject.toString());
                        try {
                            if(jsonObject.getString("code").equals("0000")){
                                session.createLoginSession(jsonObject.getString("userid"));
                                Intent next = new Intent(getApplicationContext(), MainDashboard.class);
                                startActivity(next);
                                LoginActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }else{
                                popUpDialogError(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                standardProgressDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Mobile Safari/537.36");
                return super.getHeaders();
            }
        };
        Volley.newRequestQueue(getBaseContext()).add(jsonReq);
    }

    public void popUpDialogError(String message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                })
                .show();
    }
}
