package com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.MyJobFRAGMENT;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tuudi3pl.tuudirider.Adapter.MyJobAdapter;
import com.tuudi3pl.tuudirider.Class.MyJobClass;
import com.tuudi3pl.tuudirider.Connection.URL;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.PreferenceManagerLogin;
import com.tuudi3pl.tuudirider.Utils.StandardProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobPickupFragment extends Fragment {

    RecyclerView recyclerView;

    PreferenceManagerLogin session;

    StandardProgressDialog standardProgressDialog;

    String userid,sender_address,sender_state,sender_city,sender_postcode,sender_country,receiver_address,receiver_state,
            receiver_city,receiver_postcode,receiver_country,parcel_status;

    List<MyJobClass> openJobClasses;

    private MyJobAdapter openJobAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_job_pickup, container, false);

        standardProgressDialog = new StandardProgressDialog(getActivity().getWindow().getContext());
        session = new PreferenceManagerLogin(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(PreferenceManagerLogin.USERID);

        recyclerView = v.findViewById(R.id.recyclerView);

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
        openJobAdapter = new MyJobAdapter(getContext(), openJobClasses, new MyJobAdapter.onClickJobByMonth() {
            @Override
            public void onClick(MyJobClass jobByMonthClass) {

            }
        },getActivity());

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(openJobAdapter);

        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                URL.URL_LISTING_MY_JOB_PICKUP+userid,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if(jsonObject == null){

                        }
                        standardProgressDialog.dismiss();
                        try {
                            if(jsonObject.has("listing")){
                                JSONArray arrLISTING = new JSONArray(jsonObject.getString("listing"));
                                if(arrLISTING.length() == 0){

                                }else {
                                    for (int i =arrLISTING.length()-1; i >=0; i--){
                                        JSONObject objectListing = arrLISTING.getJSONObject(i);

                                        //GET STATUS PARCEL
                                        if(objectListing.has("tracking_status")){
                                            JSONObject objParcel = new JSONObject(objectListing.getString("tracking_status"));
                                            parcel_status = objParcel.getString("status_description");
                                        }

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

                                        openJobClasses.add(new MyJobClass(
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
                                                parcel_status,
                                                objectListing.getString("parcel_type")
                                        ));
                                        openJobAdapter = new MyJobAdapter(getContext(), openJobClasses, new MyJobAdapter.onClickJobByMonth() {
                                            @Override
                                            public void onClick(MyJobClass jobByMonthClass) {
                                            }
                                        },getActivity());
                                        recyclerView.setAdapter(openJobAdapter);
                                    }
                                }
                            }else{

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
