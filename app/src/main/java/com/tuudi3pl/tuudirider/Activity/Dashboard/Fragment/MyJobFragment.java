package com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment;


import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.MyJobFRAGMENT.MyJobDeliveryFragment;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.MyJobFRAGMENT.MyJobPickupFragment;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobFragment extends Fragment {
    TextView textView_title;
    private TabLayout tabLayout;
    private ViewPager viewPager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_job, container, false);

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
        adapter.addFragment(new MyJobPickupFragment(), "PICKUP");
        adapter.addFragment(new MyJobDeliveryFragment(), "DELIVERY");
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
