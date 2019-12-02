package com.tuudi3pl.tuudirider.Activity.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.tuudi3pl.tuudirider.Activity.Dashboard.MainDashboard;
import com.tuudi3pl.tuudirider.Activity.Login.LoginActivity;
import com.tuudi3pl.tuudirider.Activity.ResetPassword.ResetPasswordActivity;
import com.tuudi3pl.tuudirider.Connection.URL;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.StandardProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class RegisterActivity extends AppCompatActivity {

    ImageView imageView_back;

    TextInputEditText email,password,confirm_password,first_name,last_name,phone_no,
            address1,address2,address3,postcode,city,state;

    String country = "Malaysia";

    StandardProgressDialog standardProgressDialog;

    Button button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        imageView_back = findViewById(R.id.imageView_back);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        phone_no = findViewById(R.id.phone_no);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        address3 = findViewById(R.id.address3);
        postcode = findViewById(R.id.postcode);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        button_register = findViewById(R.id.button_register);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")){
                    email.setError("Empty");
                }else if(password.getText().toString().equals("")){
                    password.setError("Empty");
                }else if(confirm_password.getText().toString().equals("")){
                    confirm_password.setError("Empty");
                }else if(first_name.getText().toString().equals("")){
                    first_name.setError("Empty");
                }else if(last_name.getText().toString().equals("")){
                    last_name.setError("Empty");
                }else if(phone_no.getText().toString().equals("")){
                    phone_no.setError("Empty");
                }else if(address1.getText().toString().equals("")){
                    address1.setError("Empty");
                }else if(address2.getText().toString().equals("")){
                    address2.setError("Empty");
                }else if(address3.getText().toString().equals("")){
                    address3.setError("Empty");
                }else if(postcode.getText().toString().equals("")){
                    postcode.setError("Empty");
                }else if(city.getText().toString().equals("")){
                    city.setError("Empty");
                }else if(state.getText().toString().equals("")){
                    state.setError("Empty");
                }else{
                    if(!password.getText().toString().equals(confirm_password.getText().toString())){
                        confirm_password.setError("Confirm password not same");
                    }else{
                        standardProgressDialog.show();
                        register();
                    }
                }
            }
        });

    }

    public void register(){
        StringRequest stringRequest = new StringRequest(POST,
                URL.URL_REGISTER+"email="+email.getText().toString()+"&password="+password.getText().toString()+"&confirm_password="+confirm_password.getText().toString()+"&first_name="+first_name.getText().toString()+"&last_name="+last_name.getText().toString()+"&phone_no="+phone_no.getText().toString()+"&address1="+address1.getText().toString()+"&address2="+address2.getText().toString()+"&address3="+address3.getText().toString()+"&postcode="+postcode.getText().toString()+"&city="+city.getText().toString()+"&state="+state.getText().toString()+"&country=Malaysia",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("yaw",response);
                        standardProgressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("code").equals("0000")){
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setMessage("Registration Success")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent next = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(next);
                                            }
                                        })
                                        .show();
                            }else{
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setMessage("Registration failed")
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
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        standardProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Decline Job Error",Toast.LENGTH_LONG).show();
                    }
                }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key","ea5a5063a6684896b99c952e87c2fd6b");
                params.put("email",email.getText().toString());
                params.put("password",password.getText().toString());
                params.put("confirm_password",confirm_password.getText().toString());
                params.put("phone_no",phone_no.getText().toString());
                params.put("first_name",first_name.getText().toString());
                params.put("last_name",last_name.getText().toString());
                params.put("address1",address1.getText().toString());
                params.put("address2",address2.getText().toString());
                params.put("address3",address3.getText().toString());
                params.put("postcode",postcode.getText().toString());
                params.put("city",city.getText().toString());
                params.put("state",state.getText().toString());
                params.put("country","Malaysia");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
