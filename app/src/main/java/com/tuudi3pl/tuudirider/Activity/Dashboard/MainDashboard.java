package com.tuudi3pl.tuudirider.Activity.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.internal.BaselineLayout;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.DashboardFragment;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.HistoryFragment;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.MyJobFragment;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.OpenJobFragment;
import com.tuudi3pl.tuudirider.Activity.Dashboard.Fragment.SettingFragment;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.PreferenceManagerLogin;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

public class MainDashboard extends AppCompatActivity {

    BottomNavigationView navigation;

    private static long back_pressed;

    PreferenceManagerLogin session;

    public static String which = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        session = new PreferenceManagerLogin(getApplicationContext());

        if(session.checkLogin()){
            finish();
        }else{
            navigation = findViewById(R.id.navigationView);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            BottomNavigationHelper.removeShiftMode(navigation);
            navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            setNavigationTypeface();


            if(getIntent().hasExtra("onclick")){
                if(getIntent().getStringExtra("onclick").equals("Open Jobs")){
                    navigation.setSelectedItemId(R.id.openJob);
                    which = getIntent().getStringExtra("which");
                }else if(getIntent().getStringExtra("onclick").equals("My Jobs")){
                    navigation.setSelectedItemId(R.id.myJob);
                    which = getIntent().getStringExtra("which");
                }else if(getIntent().getStringExtra("onclick").equals("Complete Jobs")){
                    navigation.setSelectedItemId(R.id.history);
                    which = getIntent().getStringExtra("which");
                }
            }else{
                Fragment fragment = new DashboardFragment();
                loadFragment(fragment);
            }
        }



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

    private static final class BottomNavigationHelper {
        @SuppressLint("RestrictedApi")
        static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShifting(false);
                item.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        }
    }

    public void setNavigationTypeface() {
        ViewGroup navigationGroup = (ViewGroup) navigation.getChildAt(0);
        for (int i = 0; i < navigationGroup.getChildCount(); i++) {
            ViewGroup navUnit = (ViewGroup) navigationGroup.getChildAt(i);
            for (int j = 0; j < navUnit.getChildCount(); j++) {
                View navUnitChild = navUnit.getChildAt(j);
                if (navUnitChild instanceof BaselineLayout) {
                    BaselineLayout baselineLayout = (BaselineLayout) navUnitChild;
                    for (int k = 0; k < baselineLayout.getChildCount(); k++) {
                        View baselineChild = baselineLayout.getChildAt(k);
                        if (baselineChild instanceof TextView) {
                            TextView textView = (TextView) baselineChild;
                            TypeFaceClass.setTypeFaceTextView(textView,getApplicationContext());
                            textView.setTextSize(10);
                        }
                    }
                }
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.dashboard:
                    fragment = new DashboardFragment();
                    loadFragment(fragment);
                    which = "";
                    return true;
                case R.id.myJob:
                    fragment = new MyJobFragment();
                    loadFragment(fragment);
                    which = "";
                    return true;
                case R.id.openJob:
                    fragment = new OpenJobFragment();
                    loadFragment(fragment);
                    which = "";
                    return true;
                case R.id.history:
                    fragment = new HistoryFragment();
                    loadFragment(fragment);
                    which = "";
                    return true;
                case R.id.setting:
                    fragment = new SettingFragment();
                    loadFragment(fragment);
                    which = "";
                    return true;
            }
            return false;
        }
    };
}
