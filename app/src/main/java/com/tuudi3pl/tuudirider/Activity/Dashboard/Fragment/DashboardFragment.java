package com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tuudi3pl.tuudirider.Activity.Dashboard.MainDashboard;
import com.tuudi3pl.tuudirider.Activity.Login.LoginActivity;
import com.tuudi3pl.tuudirider.Connection.URL;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.PreferenceManagerLogin;
import com.tuudi3pl.tuudirider.Utils.StandardProgressDialog;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    TextView textView_title,textView_email,textView_name,textView_valueOpenJob,textView_openjob,
            textView_valueMyJob,textView_myJob,textView_valueCompleteJob,textView_completeJob
            ,textView_valueOpenJobDelivery,textView_openjobDelivery
            ,textView_valueMyJobDelivery,textView_myJobDelivery,
            textView_valueCompleteJobDelivery,textView_completeJobDelivery;

    PreferenceManagerLogin session;

    StandardProgressDialog standardProgressDialog;

    String userid;

    LinearLayout linear_openJob,linear_myJob,linear_completeJob,linear_openJobDelivery,linear_myJobDelivery,linear_completeJobDelivery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        //FOR RESTRICT HTTPS
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        session = new PreferenceManagerLogin(getActivity());

        standardProgressDialog = new StandardProgressDialog(getActivity().getWindow().getContext());

        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(PreferenceManagerLogin.USERID);

        declare(v);

        //ON CLICK
        linear_openJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(),MainDashboard.class);
                next.putExtra("onclick","Open Jobs");
                startActivity(next);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        linear_openJobDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(),MainDashboard.class);
                next.putExtra("onclick","Open Jobs");
                startActivity(next);
                getActivity().overridePendingTransition(0, 0);
            }
        });
        linear_myJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(),MainDashboard.class);
                next.putExtra("onclick","My Jobs");
                startActivity(next);
                getActivity().overridePendingTransition(0, 0);
            }
        });
        linear_myJobDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(),MainDashboard.class);
                next.putExtra("onclick","My Jobs");
                startActivity(next);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        linear_completeJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(),MainDashboard.class);
                next.putExtra("onclick","Complete Jobs");
                startActivity(next);
                getActivity().overridePendingTransition(0, 0);
            }
        });
        linear_completeJobDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(),MainDashboard.class);
                next.putExtra("onclick","Complete Jobs");
                startActivity(next);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        return v;
    }

    private void declare(View v){
        textView_title = v.findViewById(R.id.textView_title);
        textView_email = v.findViewById(R.id.textView_email);
        textView_name = v.findViewById(R.id.textView_name);
        textView_valueOpenJob = v.findViewById(R.id.textView_valueOpenJob);
        textView_openjob = v.findViewById(R.id.textView_openjob);
        textView_valueMyJob = v.findViewById(R.id.textView_valueMyJob);
        textView_myJob = v.findViewById(R.id.textView_myJob);
        textView_valueCompleteJob = v.findViewById(R.id.textView_valueCompleteJob);
        textView_completeJob = v.findViewById(R.id.textView_completeJob);
        textView_valueOpenJobDelivery = v.findViewById(R.id.textView_valueOpenJobDelivery);
        textView_openjobDelivery = v.findViewById(R.id.textView_openjobDelivery);
        textView_valueMyJobDelivery = v.findViewById(R.id.textView_valueMyJobDelivery);
        textView_myJobDelivery = v.findViewById(R.id.textView_myJobDelivery);
        textView_valueCompleteJobDelivery = v.findViewById(R.id.textView_valueCompleteJobDelivery);
        textView_completeJobDelivery = v.findViewById(R.id.textView_completeJobDelivery);

        linear_openJob = v.findViewById(R.id.linear_openJob);
        linear_myJob = v.findViewById(R.id.linear_myJob);
        linear_completeJob = v.findViewById(R.id.linear_completeJob);

        //delivery
        linear_openJobDelivery = v.findViewById(R.id.linear_openJobDelivery);
        linear_myJobDelivery = v.findViewById(R.id.linear_myJobDelivery);
        linear_completeJobDelivery = v.findViewById(R.id.linear_completeJobDelivery);

        TypeFaceClass.setTypeFaceTextViewBOLD(textView_title,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_email,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_email,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_valueOpenJob,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_openjob,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_valueMyJob,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_myJob,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_valueCompleteJob,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_completeJob,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_valueOpenJobDelivery,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_openjobDelivery,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_valueMyJobDelivery,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_myJobDelivery,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_valueCompleteJobDelivery,getActivity());
        TypeFaceClass.setTypeFaceTextView(textView_completeJobDelivery,getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        standardProgressDialog.show();
        getProfile();
    }

    private void getProfile(){
        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                URL.URL_PROFILE+userid,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.has("profile")){
                                JSONObject profileOBJ = new JSONObject(jsonObject.getString("profile"));
                                textView_name.setText(profileOBJ.getString("first_name")+" "+profileOBJ.getString("last_name"));
                                textView_email.setText(profileOBJ.getString("email"));
                                getCountJob();
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
        Volley.newRequestQueue(getActivity()).add(jsonReq);
    }

    private void getCountJob(){
        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                URL.URL_COUNT_JOB+userid,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        standardProgressDialog.dismiss();
                        try {
                            if(jsonObject.has("data")){
                                JSONObject dataOBJ = new JSONObject(jsonObject.getString("data"));
                                textView_valueOpenJob.setText(dataOBJ.getString("openpickupjob"));
                                textView_valueOpenJobDelivery.setText(dataOBJ.getString("opendeliverjob"));
                                textView_valueMyJob.setText(dataOBJ.getString("mypickupjob"));
                                textView_valueMyJobDelivery.setText(dataOBJ.getString("mydeliverjob"));
                                textView_valueCompleteJob.setText(dataOBJ.getString("completepickupjob"));
                                textView_valueCompleteJobDelivery.setText(dataOBJ.getString("completedeliverjob"));
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
        Volley.newRequestQueue(getActivity()).add(jsonReq);
    }
}
