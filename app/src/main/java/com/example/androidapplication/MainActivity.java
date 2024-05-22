package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @SuppressLint("DefaultLocale")
    public void BMI_click(View v){
        double weight=Double.parseDouble(((EditText)findViewById(R.id.BMI_weight)).getText().toString());
        double height=Double.parseDouble(((EditText)findViewById(R.id.BMI_height)).getText().toString());
        double bmi=weight/height/height;
        if(bmi<18){
            ((TextView)findViewById(R.id.BMI_title)).setText(String.format("您的BMI为\n%.2f\n体重偏轻",bmi));
        }
        else if(bmi>24){
            ((TextView)findViewById(R.id.BMI_title)).setText(String.format("您的BMI为\n%.2f\n体重偏重",bmi));
        }
        else{
            ((TextView)findViewById(R.id.BMI_title)).setText(String.format("您的BMI为\n%.2f\n体重正常",bmi));
        }
    }
}