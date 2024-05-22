package com.example.androidapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateTransform extends AppCompatActivity {
    double dollar=0.1383,euro=0.1271,won=186.91;
    int lastOperate;
    @SuppressLint("DefaultLocale")
    ActivityResultLauncher<Intent>rateConfig=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{//处理返回
        Intent intent=result.getData();
        if(intent!=null){
            //Log.i("rateConfig","intent不为空");
            dollar=intent.getDoubleExtra("dollar",0.1383);
            euro=intent.getDoubleExtra("euro",0.1271);
            won=intent.getDoubleExtra("won",186.91);
            try{
                double rmb=Double.parseDouble(((EditText)findViewById(R.id.rateTransform_RMB)).getText().toString());
                if(lastOperate==R.id.rateTransform_dollar){
                    ((TextView)findViewById(R.id.rateTransform_default)).setText(String.format("%.2f",rmb*dollar));
                }
                else if(lastOperate==R.id.rateTransform_euro){
                    ((TextView)findViewById(R.id.rateTransform_default)).setText(String.format("%.2f",rmb*euro));
                }
                else if(lastOperate==R.id.rateTransform_won){
                    ((TextView)findViewById(R.id.rateTransform_default)).setText(String.format("%.2f",rmb*won));
                }
            }
            catch(NumberFormatException e){
                Log.i("rateConfig","未输入人民币值");
            }
        }
        else{
            Toast.makeText(RateTransform.this, "数据修改失败", Toast.LENGTH_SHORT).show();
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_transform);
    }
    @SuppressLint("DefaultLocale")
    public void rateTransform(View v){
        try{
            double rmb=Double.parseDouble(((EditText)findViewById(R.id.rateTransform_RMB)).getText().toString());
            if(v==findViewById(R.id.rateTransform_dollar)){
                ((TextView)findViewById(R.id.rateTransform_default)).setText(String.format("%.2f",rmb*dollar));
            }
            else if(v==findViewById(R.id.rateTransform_euro)){
                ((TextView)findViewById(R.id.rateTransform_default)).setText(String.format("%.2f",rmb*euro));
            }
            else{
                ((TextView)findViewById(R.id.rateTransform_default)).setText(String.format("%.2f",rmb*won));
            }
            lastOperate=v.getId();
        }
        catch (NumberFormatException e) {
            Toast.makeText(this, "请输入正确的数据！", Toast.LENGTH_SHORT).show();
        }
    }
    public void rateConfig(View v){
        Log.i("rateConfig","准备进入配置页面");
        Intent intent=new Intent(this, RateConfig.class);
        intent.putExtra("dollar",dollar);
        intent.putExtra("euro",euro);
        intent.putExtra("won",won);
        rateConfig.launch(intent);
    }
}