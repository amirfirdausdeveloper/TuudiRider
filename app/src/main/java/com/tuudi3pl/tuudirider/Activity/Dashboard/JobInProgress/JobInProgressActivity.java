package com.tuudi3pl.tuudirider.Activity.Dashboard.JobInProgress;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tuudi3pl.tuudirider.Activity.Dashboard.JobAccept.JobAcceptActivity;
import com.tuudi3pl.tuudirider.Activity.Dashboard.MainDashboard;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Signature.SignatureActivity;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Tracking.TrackingActivity;
import com.tuudi3pl.tuudirider.Connection.URL;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.PreferenceManagerLogin;
import com.tuudi3pl.tuudirider.Utils.StandardProgressDialog;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JobInProgressActivity extends AppCompatActivity {

    String order_id,userid,parcel_status,CN;

    ImageView imageView_back,imageView_track;

    TextView textView_title,textView_order_id,textView_date,textView_delivery_title,textView_delivery_type,textView_weight,
            textView_title2,textView_pickup_address,textView_pickup_email,textView_pickup_name,textView_pickup_phone,
            textView_title3,textView_delivery_address,textView_delivery_email,textView_delivery_name,textView_delivery_no,
            textView_title5,textView_remarks,textView_cn_title,textView_cn,textView_company_pickup,textView_company_delivery
            ,textView_words33,textView_status_parce3;

    Button button_accept,button_accept2;

    PreferenceManagerLogin session;

    StandardProgressDialog standardProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_in_progress);

        //FOR RESTRICT HTTPS
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());
        session = new PreferenceManagerLogin(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(PreferenceManagerLogin.USERID);

        order_id = getIntent().getStringExtra("order_id");

        declare();


        //ON CLICK FUNCTION
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageView_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), TrackingActivity.class);
                next.putExtra("cn",CN);
                startActivity(next);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });



    }

    private void declare() {
        imageView_back = findViewById(R.id.imageView_back);
        textView_title = findViewById(R.id.textView_title);
        textView_order_id = findViewById(R.id.textView_order_id);
        textView_date = findViewById(R.id.textView_date);
        textView_delivery_title = findViewById(R.id.textView_delivery_title);
        textView_delivery_type = findViewById(R.id.textView_delivery_type);
        textView_weight = findViewById(R.id.textView_weight);
        textView_title2 = findViewById(R.id.textView_title2);
        textView_pickup_address = findViewById(R.id.textView_pickup_address);
        textView_pickup_email = findViewById(R.id.textView_pickup_email);
        textView_pickup_name = findViewById(R.id.textView_pickup_name);
        textView_pickup_phone = findViewById(R.id.textView_pickup_phone);
        textView_title3 = findViewById(R.id.textView_title3);
        textView_delivery_address = findViewById(R.id.textView_delivery_address);
        textView_delivery_email = findViewById(R.id.textView_delivery_email);
        textView_delivery_name = findViewById(R.id.textView_delivery_name);
        textView_delivery_no = findViewById(R.id.textView_delivery_no);
        textView_title5 = findViewById(R.id.textView_title5);
        textView_remarks = findViewById(R.id.textView_remarks);
        button_accept = findViewById(R.id.button_accept);
        button_accept2 = findViewById(R.id.button_accept2);
        textView_cn_title = findViewById(R.id.textView_cn_title);
        textView_cn = findViewById(R.id.textView_cn);
        textView_company_pickup = findViewById(R.id.textView_company_pickup);
        textView_company_delivery = findViewById(R.id.textView_company_delivery);
        imageView_track = findViewById(R.id.imageView_track);
        textView_words33 = findViewById(R.id.textView_words33);
        textView_status_parce3 = findViewById(R.id.textView_status_parce3);

        TypeFaceClass.setTypeFaceTextView(textView_words33,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_status_parce3,getApplicationContext());
        TypeFaceClass.setTypeFaceTextViewBOLD(textView_title,getApplicationContext());
        TypeFaceClass.setTypeFaceTextViewBOLD(textView_delivery_title,getApplicationContext());
        TypeFaceClass.setTypeFaceTextViewBOLD(textView_title2,getApplicationContext());
        TypeFaceClass.setTypeFaceTextViewBOLD(textView_title3,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_order_id,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_date,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_delivery_type,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_weight,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_pickup_address,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_pickup_email,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_pickup_name,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_pickup_phone,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_delivery_address,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_delivery_email,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_delivery_name,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_delivery_no,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_title5,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_remarks,getApplicationContext());
        TypeFaceClass.setTypeFaceTextViewBOLD(textView_cn_title,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_cn,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_company_pickup,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_company_delivery,getApplicationContext());
        TypeFaceClass.setTypeFaceButton(button_accept,getApplicationContext());
        TypeFaceClass.setTypeFaceButton(button_accept2,getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();

        standardProgressDialog.show();
        getData();
    }

    private void getData(){
        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                URL.URL_JOB_DETAILS+order_id+"&userid="+userid,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        standardProgressDialog.dismiss();
                        try {
                            JSONArray arrDetais = new JSONArray(jsonObject.getString("job_details"));

                            for (int i =0; i < arrDetais.length(); i++){
                                JSONObject obj = arrDetais.getJSONObject(i);

                                JSONObject objectParace = new JSONObject(obj.getString("tracking_status"));
                                parcel_status = objectParace.getString("status_id");
                                textView_status_parce3.setText(obj.getString("parcel_type"));
                                textView_cn.setText(obj.getString("CN"));

                                Log.d("parcel_status",parcel_status);
                                getStatusButton(parcel_status);

                                CN = obj.getString("CN");
                                textView_order_id.setText("TD"+order_id);
                                textView_date.setText(obj.getString("date"));
                                textView_delivery_type.setText(obj.getString("delivery_type"));
                                textView_weight.setText(obj.getString("delivery_weight"));
                                textView_remarks.setText(obj.getString("remarks"));

                                //SENDER INFORMATION
                                JSONObject SENDER = new JSONObject(obj.getString("sender_address"));
                                textView_pickup_address.setText(
                                        SENDER.getString("address") +" "+SENDER.getString("postcode")
                                                +" "+SENDER.getString("city")+" "+SENDER.getString("state")
                                                +" "+SENDER.getString("country")
                                );
                                textView_pickup_email.setText(SENDER.getString("email"));
                                textView_pickup_name.setText(SENDER.getString("fullname"));
                                textView_pickup_phone.setText(SENDER.getString("phone"));
                                textView_company_pickup.setText(SENDER.getString("company"));

                                //DELIVERY INFORMATION
                                JSONObject DELIVERY = new JSONObject(obj.getString("receiver_address"));
                                textView_delivery_address.setText(
                                        DELIVERY.getString("address") +" "+DELIVERY.getString("postcode")
                                                +" "+DELIVERY.getString("city")+" "+DELIVERY.getString("state")
                                                +" "+DELIVERY.getString("country")
                                );
                                textView_delivery_email.setText(DELIVERY.getString("email"));
                                textView_delivery_name.setText(DELIVERY.getString("fullname"));
                                textView_delivery_no.setText(DELIVERY.getString("phone"));
                                textView_company_delivery.setText(DELIVERY.getString("company"));

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
        Volley.newRequestQueue(getApplicationContext()).add(jsonReq);
    }

    private void updatestatusButton(String statusCode, String remarks, final String message){
        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                URL.URL_UPDATE_STATUS_BUTTON+CN+"&status="+statusCode+"&remarks="+remarks+"&receiver_name=&userid="+userid,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        new AlertDialog.Builder(JobInProgressActivity.this)
                                .setCancelable(false)
                                .setMessage("Status Updated to "+message)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        recreate();
                                    }
                                })
                                .show();
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
        Volley.newRequestQueue(getApplicationContext()).add(jsonReq);
    }

    private void getStatusButton(final String statusCode){
        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                "https://tuudi3pl.com/riderapi/statuslist.php?key=ea5a5063a6684896b99c952e87c2fd6b",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray arr = new JSONArray(jsonObject.getString("status"));
                            for (int i =0; i < arr.length(); i++){
                                JSONObject obj = arr.getJSONObject(i);
                                if(statusCode.equals("10")  || parcel_status.equals("13")){
                                    if(obj.getString("id").equals("8")){
                                        button_accept.setText(obj.getString("label_display"));
                                        button_accept2.setVisibility(View.GONE);
                                    }
                                }else if(parcel_status.equals("8")){
                                    if(obj.getString("id").equals("9")){
                                        button_accept.setText(obj.getString("label_display"));
                                    }
                                    button_accept2.setText("Pending Collection");

                                }else if(parcel_status.equals("9") || parcel_status.equals("3")){
                                    if(obj.getString("id").equals("12")){
                                        button_accept.setText(obj.getString("label_display"));
                                        button_accept2.setVisibility(View.GONE);
                                    }
                                }else if(parcel_status.equals("12")){
                                    if(obj.getString("id").equals("4")){
                                        button_accept.setText(obj.getString("label_display"));
                                    }
                                    if(obj.getString("id").equals("3")){
                                        button_accept2.setText(obj.getString("label_display"));
                                    }
                                }

                                button_accept.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(statusCode.equals("10")  || parcel_status.equals("13")){
                                            updatestatusButton("8","",button_accept.getText().toString());
                                        }else if(parcel_status.equals("8")){
                                            updatestatusButton("9","",button_accept.getText().toString());
                                        }else if(parcel_status.equals("9")  || parcel_status.equals("3")){
                                            updatestatusButton("12","",button_accept.getText().toString());
                                        }else if(parcel_status.equals("12")){
                                            Intent next = new Intent(getApplicationContext(), SignatureActivity.class);
                                            next.putExtra("order_id",order_id);
                                            next.putExtra("CN",CN);
                                            startActivity(next);
                                        }
                                    }
                                });

                                button_accept2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(parcel_status.equals("8")){
                                            updatestatusButton("13","",button_accept2.getText().toString());
                                        }else if(parcel_status.equals("12")){
                                            updatestatusButton("3","",button_accept2.getText().toString());
                                        }
                                    }
                                });
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
        Volley.newRequestQueue(getApplicationContext()).add(jsonReq);
    }
}
