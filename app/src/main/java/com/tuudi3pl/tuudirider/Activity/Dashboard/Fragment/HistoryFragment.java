package com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.material.tabs.TabLayout;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.MyJobFRAGMENT.MyJobDeliveryFragment;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.MyJobFRAGMENT.MyJobPickupFragment;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.historyFRAGMENT.HistoryDeliveryFragment;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.historyFRAGMENT.HistoryPickupFragment;
import com.tuudi3pl.tuudirider.Activity.Dashboard.MainDashboard;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    TextView textView_title;
    private TabLayout tabLayout;
    private ViewPager viewPager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        //FOR RESTRICT HTTPS
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        textView_title = v.findViewById(R.id.textView_title);
        viewPager = v.findViewById(R.id.viewpager);
        tabLayout = v.findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
        TypeFaceClass.setTypeFaceTextViewBOLD(textView_title,getActivity());
        setupTab(viewPager);




        return v;
    }

    private void setupTab(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new HistoryPickupFragment(), "PICKUP");
        adapter.addFragment(new HistoryDeliveryFragment(), "DELIVERY");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
