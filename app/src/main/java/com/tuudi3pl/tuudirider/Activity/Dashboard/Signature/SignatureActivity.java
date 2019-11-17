package com.tuudi3pl.tuudirider.Activity.Dashboard.Signature;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tuudi3pl.tuudirider.Activity.Dashboard.JobInProgress.JobInProgressActivity;
import com.tuudi3pl.tuudirider.Activity.Dashboard.MainDashboard;
import com.tuudi3pl.tuudirider.Connection.URL;
import com.tuudi3pl.tuudirider.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.tuudi3pl.tuudirider.Utils.MultipartUtility;
import com.tuudi3pl.tuudirider.Utils.PreferenceManagerLogin;
import com.tuudi3pl.tuudirider.Utils.StandardProgressDialog;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignatureActivity extends AppCompatActivity {

    private static final int SIGNATURE_REQUEST_CODE = 11;

    private Bitmap mSignatureImg;

    ImageView iv_sign;

    TextView textView_title;

    EditText consignmentET,nameET,icET;

    String order_id,userid,CN;

    ImageView imageView_back;

    Button button_submit;
    PreferenceManagerLogin session;

    StandardProgressDialog standardProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());
        session = new PreferenceManagerLogin(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(PreferenceManagerLogin.USERID);

        order_id = getIntent().getStringExtra("order_id");
        CN = getIntent().getStringExtra("CN");

        textView_title = findViewById(R.id.textView_title);
        consignmentET = findViewById(R.id.consignmentET);
        nameET = findViewById(R.id.nameET);
        icET = findViewById(R.id.icET);
        iv_sign = findViewById(R.id.iv_sign);
        imageView_back = findViewById(R.id.imageView_back);
        button_submit = findViewById(R.id.button_submit);


        consignmentET.setText(CN);

        iv_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignatureActivity.this, sign.class);
                startActivityForResult(intent, SIGNATURE_REQUEST_CODE);
            }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameET.getText().toString().equals("") || icET.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill in the value",Toast.LENGTH_LONG).show();
                }else if(mSignatureImg == null){
                    Toast.makeText(getApplicationContext(),"Please complete signature first",Toast.LENGTH_LONG).show();
                }else{

                    new AlertDialog.Builder(SignatureActivity.this)
                            .setCancelable(false)
                            .setMessage("are you sure want to submit?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    standardProgressDialog.show();
                                    try {
                                        savebitmap(mSignatureImg);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();

                }
            }
        });
    }

    private File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator+userid+"signature.jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        updatestatusButton(f);
        return f;
    }

    private void uploadData(File f) throws IOException {
        String charset = "UTF-8";
        String requestURL = "https://tuudi3pl.com/riderapi/signature.php";
        MultipartUtility multipart = new MultipartUtility(requestURL, charset);
        multipart.addFormField("key", "ea5a5063a6684896b99c952e87c2fd6b");
        multipart.addFormField("order_id", order_id);
        multipart.addFormField("recipient_name", nameET.getText().toString());
        multipart.addFormField("recipient_nric", icET.getText().toString());
        multipart.addFilePart("uploaded_file",f);
        String response = String.valueOf(multipart.finish()); // response from server.
        Log.d("response",response);

        try {
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++){
                standardProgressDialog.dismiss();
                JSONObject obj = arr.getJSONObject(i);
                if(obj.getString("code").equals("0000")){
                    Intent next = new Intent(getApplicationContext(), MainDashboard.class);
                    startActivity(next);
                }else{
                    new AlertDialog.Builder(SignatureActivity.this)
                            .setCancelable(false)
                            .setMessage(obj.getString("message"))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case SIGNATURE_REQUEST_CODE:
                    byte[] bytes = data.getByteArrayExtra("sign_image");
                    mSignatureImg = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if(mSignatureImg != null) {
                        iv_sign.setImageBitmap(mSignatureImg);
                    }
                    break;
            }
        }
    }

    private void updatestatusButton(final File f){
        JsonObjectRequest jsonReq = new JsonObjectRequest(
                Request.Method.GET,
                URL.URL_UPDATE_STATUS_BUTTON+CN+"&status=4&remarks=&receiver_name="+nameET.getText().toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            uploadData(f);
                        } catch (IOException e) {
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
