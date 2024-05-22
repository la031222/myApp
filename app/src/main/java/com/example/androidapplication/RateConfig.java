package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RateConfig extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_config);
        ((EditText)findViewById(R.id.rateConfig_dollar)).setText(String.valueOf(getIntent().getDoubleExtra("dollar",0.1383)));
        ((EditText)findViewById(R.id.rateConfig_euro)).setText(String.valueOf(getIntent().getDoubleExtra("euro",0.1271)));
        ((EditText)findViewById(R.id.rateConfig_won)).setText(String.valueOf(getIntent().getDoubleExtra("won",186.91)));
    }
    public void rateConfig_save(View v){
        try{
            getIntent().putExtra("dollar",Double.parseDouble(((EditText)findViewById(R.id.rateConfig_dollar)).getText().toString()));
            getIntent().putExtra("euro",Double.parseDouble(((EditText)findViewById(R.id.rateConfig_euro)).getText().toString()));
            getIntent().putExtra("won",Double.parseDouble(((EditText)findViewById(R.id.rateConfig_won)).getText().toString()));
            setResult(R.id.rateConfig_save,getIntent());
            finish();
        }
        catch(Exception e){
            Toast.makeText(this, "请输入正确的数据！", Toast.LENGTH_SHORT).show();
        }

    }
}