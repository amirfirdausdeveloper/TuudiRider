package com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tuudi3pl.tuudirider.Activity.Dashboard.JobAccept.JobAcceptActivity;
import com.tuudi3pl.tuudirider.Activity.Dashboard.MainDashboard;
import com.tuudi3pl.tuudirider.Activity.Login.LoginActivity;
import com.tuudi3pl.tuudirider.Adapter.OpenJobAdapter;
import com.tuudi3pl.tuudirider.Class.OpenJobClass;
import com.tuudi3pl.tuudirider.Connection.URL;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.PreferenceManagerLogin;
import com.tuudi3pl.tuudirider.Utils.StandardProgressDialog;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenJobFragment extends Fragment {

    TextView textView_title;

    RecyclerView recyclerView;

    PreferenceManagerLogin session;

    StandardProgressDialog standardProgressDialog;

    String userid,sender_address,sender_state,sender_city,sender_postcode,sender_country,receiver_address,receiver_state,
            receiver_city,receiver_postcode,receiver_country;

    List<OpenJobClass> openJobClasses;

    private OpenJobAdapter openJobAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_open_job, container, false);

        //FOR RESTRICT HTTPS
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        standardProgressDialog = new StandardProgressDialog(getActivity().getWindow().getContext());
        session = new PreferenceManagerLogin(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(PreferenceManagerLogin.USERID);

        textView_title = v.findViewById(R.id.textView_title);
        recyclerView = v.findViewById(R.id.recyclerView);

        TypeFaceClass.setTypeFaceTextViewBOLD(textView_title,getActivity());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        standardProgressDialog.show();
        getJob();
    }

    private void getJob(){

        recyclerView.setHasFixedSize(false);
        openJobClasses = new ArrayList<>();
        openJobAdapter = new OpenJobAdapter(getContext(), openJobClasses, new OpenJobAdapter.onClickJobByMonth() {
            @Override
            public void onClick(OpenJobClass jobByMonthClass) {

            }
        },getActivity());

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(openJobAdapter);

        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                URL.URL_LISTING_JOB,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("jsonObject",jsonObject.toString());
                        standardProgressDialog.dismiss();
                        try {
                            if(jsonObject.has("listing")){
                                JSONArray arrLISTING = new JSONArray(jsonObject.getString("listing"));
                                if(arrLISTING.length() == 0){
                                    dialogNoJob();
                                }else {
                                    for (int i =arrLISTING.length()-1; i >=0; i--){
                                        Log.d("arr", String.valueOf(i));
                                        JSONObject objectListing = arrLISTING.getJSONObject(i);

                                        //SENDER ADDRESS
                                        if(objectListing.has("sender_address")){
                                            JSONObject sender = new JSONObject(objectListing.getString("sender_address"));
                                            sender_address = sender.getString("address");
                                            sender_state = sender.getString("state");
                                            sender_city = sender.getString("city");
                                            sender_postcode = sender.getString("postcode");
                                            sender_country = sender.getString("country");
                                        }

                                        //RECEIVER ADDRESS
                                        if(objectListing.has("receiver_address")){
                                            JSONObject sender = new JSONObject(objectListing.getString("receiver_address"));
                                            receiver_address = sender.getString("address");
                                            receiver_state = sender.getString("state");
                                            receiver_city = sender.getString("city");
                                            receiver_postcode = sender.getString("postcode");
                                            receiver_country = sender.getString("country");
                                        }

                                        openJobClasses.add(new OpenJobClass(
                                                objectListing.getString("order_id"),
                                                objectListing.getString("CN"),
                                                objectListing.getString("shipping_type"),
                                                objectListing.getString("date"),
                                                objectListing.getString("status_id"),
                                                objectListing.getString("delivery_type"),
                                                objectListing.getString("delivery_weight"),
                                                objectListing.getString("remarks"),
                                                sender_address,
                                                sender_state,
                                                sender_city,
                                                sender_postcode,
                                                sender_country,
                                                receiver_address,
                                                receiver_state,
                                                receiver_city,
                                                receiver_postcode,
                                                 receiver_country,
                                                "openjob"
                                        ));
                                        openJobAdapter = new OpenJobAdapter(getContext(), openJobClasses, new OpenJobAdapter.onClickJobByMonth() {
                                            @Override
                                            public void onClick(OpenJobClass jobByMonthClass) {
                                                 }
                                        },getActivity());
                                        recyclerView.setAdapter(openJobAdapter);
                                    }
                                }
                            }else{
                                dialogNoJob();
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

    public void dialogNoJob() {
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("No Job Available")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent next = new Intent(getActivity(), MainDashboard.class);
                        startActivity(next);
                    }
                })
                .show();
    }
}
