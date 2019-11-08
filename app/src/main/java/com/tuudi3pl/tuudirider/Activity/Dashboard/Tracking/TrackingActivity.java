package com.tuudi3pl.tuudirider.Activity.Dashboard.Tracking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

public class TrackingActivity extends AppCompatActivity {

    ImageView imageView_back;

    TextView textView_title;

    WebView webView;

    String cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        imageView_back = findViewById(R.id.imageView_back);
        textView_title = findViewById(R.id.textView_title);
        webView = findViewById(R.id.webView);

        TypeFaceClass.setTypeFaceTextViewBOLD(textView_title,getApplicationContext());
        cn = getIntent().getStringExtra("cn");

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);

        webView.loadUrl("https://tuudi3pl.com/v2/tracking.php?tid="+cn);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
