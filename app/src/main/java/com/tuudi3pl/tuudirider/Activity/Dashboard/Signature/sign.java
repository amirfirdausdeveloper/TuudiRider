package com.tuudi3pl.tuudirider.Activity.Dashboard.Signature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tuudi3pl.tuudirider.R;

import java.io.ByteArrayOutputStream;

import kivaaz.com.signaturelibrary.Signature.Signature.SignatureView;

public class sign extends AppCompatActivity {

    private SignatureView mSignatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mSignatureView = findViewById(R.id.signatureview);
        FloatingActionButton fABAccept = findViewById(R.id.fab_accept);
        FloatingActionButton fABCancel = findViewById(R.id.fab_cancel);
        setupSignature();

        fABAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("sign_image", compressSign(mSignatureView.getCanvasBitmap()));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        fABCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void setupSignature() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mSignatureView.setCanvasColor(Color.WHITE);
        mSignatureView.setPenColor(Color.BLACK);
        mSignatureView.setStrokeWidth(10);
        mSignatureView.setupDrawing(metrics);
    }

    private byte[] compressSign(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        return stream.toByteArray();
    }
}
