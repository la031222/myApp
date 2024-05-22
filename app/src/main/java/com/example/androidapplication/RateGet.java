package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class RateGet extends AppCompatActivity implements Runnable{
    Handler handler=new Handler(Objects.requireNonNull(Looper.myLooper())){
        public void handleMessage(Message msg){
            ((TextView)findViewById(R.id.rateGet_result)).setText(msg.obj.toString());
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_get);
    }
    public void rateGet(View v){
        new Thread(this).start();
    }
    @Override
    public void run() {
        Log.i("rateGet","线程已启动");
        try {
            Document doc=Jsoup.connect("https://wocha.cn/huilv/?jinri").get();
            Elements trs=doc.select("#Lt > article > div.ahh > table:nth-child(3)").first().getElementsByTag("tr");
            trs.remove(trs.first());
            trs.remove(trs.last());
            StringBuilder result=new StringBuilder();
            for(Element tr:trs){
                result.append(tr.getElementsByTag("td").first().text()).append("->").append(tr.getElementsByTag("td").get(5).text()).append("\n");
            }
            Message message=handler.obtainMessage();
            message.obj=result;
            handler.sendMessage(message);
        } catch (IOException e) {
            Toast.makeText(this, "获取数据失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }
}