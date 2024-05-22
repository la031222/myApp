package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MoneyActivity extends AppCompatActivity implements Runnable{
    private static final String TAG = "Rate";
    public float dollarRate = 0.1383f;
    public float euroRate = 0.1271f;
    public float wonRate = 186.91f;

    public void run() {
        Log.i(TAG, "run: run()......");
        Handler handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        Intent config1 = getIntent();
        dollarRate = config1.getFloatExtra("dollar_rate",0.1383f);
        euroRate = config1.getFloatExtra("euro_rate",0.1271f);
        wonRate = config1.getFloatExtra("won_rate",186.91f);
        //开启子线程
        Thread t = new Thread(this);
        t.start();
    };
    }
    public void MoneyClick(View btn){
        //获取按钮
        Button btn_dollar = findViewById(R.id.btn_dollar);
        Button btn_euro = findViewById(R.id.btn_euro);
        Button btn_won = findViewById(R.id.btn_won);
        //获取收入数据
        EditText rmb_input = findViewById(R.id.rmb);
        TextView result = findViewById(R.id.result);
        String rmb = rmb_input.getText().toString();
        //判断用户输入数据是否正确
        if(rmb.isEmpty()){
            //提示消息(显示时长由LENGTH_LONG决定)
            Toast.makeText(this,"请您输入正确的数据!", Toast.LENGTH_LONG).show();
            //不加show不能在日志的用户界面里出现
        }
        else{
            float input = Float.parseFloat(rmb);
            float rmb_result = 0f;
            //判断用户按钮
            if(btn.getId() == R.id.btn_dollar){
                rmb_result = input * dollarRate;
            }
            else if (btn.getId() == R.id.btn_euro) {
                rmb_result = input * euroRate;
            }
            else if (btn.getId() == R.id.btn_won) {
                rmb_result = input * wonRate;
            }
            //结果输出
            String rmb_str_result = String.format("%.2f",rmb_result);
            result.setText(rmb_str_result);
        }
    }
    public void clickOpen(View v){

        //打开新窗口并传入参数
        Intent config = new Intent(MoneyActivity.this,ConfigActivity.class);
        //传递参数
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);
        startActivity(config);
        //生成日志
        Log.i(TAG,"clickOpen:dollarRate=" + dollarRate);
        Log.i(TAG,"clickOpen:euroRate=" + euroRate);
        Log.i(TAG,"clickOpen:wonRate=" + wonRate);
    }
}
