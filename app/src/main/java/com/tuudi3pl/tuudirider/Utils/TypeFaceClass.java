package com.tuudi3pl.tuudirider.Utils;

import android.content.Context;
import android.graphics.Typeface;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class TypeFaceClass {

    public static void setTypeFaceEditText(EditText editText, Context context){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf"));
    }

    public static void setTypeFaceTextView(TextView textView, Context context){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf"));
    }
    public static void setTypeFaceButton(Button button, Context context){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf"));
    }

    public static void setTypeFaceTextInputEditText(TextInputEditText textInputEditText, Context context){
        textInputEditText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf"));
    }

    public static void setTypeFaceCheckBox(CheckBox checkBox, Context context){
        checkBox.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf"));
    }

    public static void setTypeFaceTextViewBOLD(TextView textView, Context context){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Bold.ttf"));
    }
}