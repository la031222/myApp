package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


public class ConfigActivity extends AppCompatActivity {
    float dollar1,euro1,won1;
    private static final String TAG = "Rate";
    EditText dollar_edit,euro_edit,won_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        //接收参数
        Intent intent = getIntent();
        float dollar2 = intent.getFloatExtra("dollar_rate_key",1.1f);
        float euro2 = intent.getFloatExtra("euro_rate_key",1.1f);
        float won2 = intent.getFloatExtra("won_rate_key",1.1f);
        //生成日志
        Log.i(TAG,"onCreate:dollar2"+dollar2);
        Log.i(TAG,"onCreate:euro2"+euro2);
        Log.i(TAG,"onCreate:won2"+won2);
        //显示在控件中
        dollar_edit = findViewById(R.id.dollar_edit);
        euro_edit = findViewById(R.id.euro_edit);
        won_edit = findViewById(R.id.won_edit);

        dollar_edit.setText(String.valueOf(dollar2));
        euro_edit.setText(String.valueOf(euro2));
        won_edit.setText(String.valueOf(won2));
    }
    public void configClick(View btn){
        Button btn_save = findViewById(R.id.btn_save);

        //获取用户输入
        dollar_edit = findViewById(R.id.dollar_edit);
        euro_edit = findViewById(R.id.euro_edit);
        won_edit = findViewById(R.id.won_edit);

        //转换为float
        dollar1  = Float.parseFloat(dollar_edit.getText().toString());
        euro1 = Float.parseFloat(euro_edit.getText().toString());
        won1 = Float.parseFloat(won_edit.getText().toString());

        Intent intent1 = new Intent(ConfigActivity.this,MoneyActivity.class);
        intent1.putExtra("dollar_rate",dollar1);
        intent1.putExtra("euro_rate",euro1);
        intent1.putExtra("won_rate",won1);
        startActivity(intent1);
    }
}
