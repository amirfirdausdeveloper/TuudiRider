package com.tuudi3pl.tuudirider.Activity.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.tuudi3pl.tuudirider.Activity.Dashboard.MainDashboard;
import com.tuudi3pl.tuudirider.Activity.Login.LoginActivity;
import com.tuudi3pl.tuudirider.Connection.URL;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.PreferenceManagerLogin;
import com.tuudi3pl.tuudirider.Utils.StandardProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {

    TextInputEditText et_username,et_password;

    Button button_reset;

    StandardProgressDialog standardProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        et_username = findViewById(R.id.et_username);
        button_reset = findViewById(R.id.button_reset);

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_username.getText().toString().equals("")){
                    et_username.setError("Empty");
                }else{
                    standardProgressDialog.show();
                    resetPassword();
                }
            }
        });


    }

    public void resetPassword(){
        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                URL.URL_RESETPASSWORD+et_username.getText().toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        standardProgressDialog.dismiss();
                        Log.d("yaw",jsonObject.toString());
                        try {
                            if(jsonObject.getString("code").equals("0000")){
                                new AlertDialog.Builder(ResetPasswordActivity.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setMessage("Please check your email for password reset")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent next = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(next);
                                            }
                                        })
                                        .show();
                            }else{
                                new AlertDialog.Builder(ResetPasswordActivity.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setMessage("Email not found")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();
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
}
