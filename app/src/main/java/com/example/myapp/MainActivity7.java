package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity7 extends AppCompatActivity implements Runnable{

    private static final String TAG = "Net";

    Handler handler;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        show = findViewById(R.id.net_show);

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                //处理返回
                if (msg.what==5){
                    String str = (String) msg.obj;
                    Log.i(TAG, "handleMessage: str="+str);
                    show.setText(str);
                }
                super.handleMessage(msg);
            }
        };
    }


    public void onClick(View btn){
        Log.i(TAG, "onCreate: start Thread");
        Thread t = new Thread(this);
        t.start();//this.run()
    }

    @Override
    public void run() {
        Log.i(TAG, "run: 子线程run().....");

        //获取网络数据
        URL url = null;
        Bundle fan = new Bundle();
        try {
            Document  doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Elements tables = doc.getElementsByTag("table");
            Element table2 = tables.get(1);
            Log.i("tag", "run:table2"+table2);
            Elements rows = table2.getElementsByTag("tr");
            for(Element row : rows){
                Elements tds = row.getElementsByTag("td");
                if (tds.size()>6){
                    String rname = tds.get(0).text();
                    String rateStr = tds.get(5).text();
                    //Log.i("tag","run:"+rname+"==>" + rateStr);
                    if (rname.equals("欧元")){
                        fan.putString("euro", rateStr);
                        Log.i("tag","run:欧元汇率= "+ rateStr);
                    } else if (rname.equals("美元")) {
                        Log.i("tag","run:美元汇率= "+ rateStr);
                        fan.putString("dollar",rateStr);
                    } else if (rname.equals("韩国元")) {
                        Log.i("tag","run:韩元汇率= "+ rateStr);
                        fan.putString("won",rateStr);
                    }
                }
            }
            Element tr = rows.get(6);

            //url = new URL("https://www.boc.cn/sourcedb/whpj/");
            //HttpURLConnection http = (HttpURLConnection) url.openConnection();
            //InputStream in = http.getInputStream();
            //String html = inputStream2String(in);
            //Log.i("tag","run:html"+html);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            // 发生 URL 异常时的处理
            handler.sendEmptyMessage(0); // 发送空消息或者特定错误码消息，以便在 UI 上给出提示
        } catch (IOException e) {
            e.printStackTrace();
            // 发生 IO 异常时的处理
            handler.sendEmptyMessage(1); // 发送空消息或者特定错误码消息，以便在 UI 上给出提示
        }
        //发送数据回主线程
        Message msg = handler.obtainMessage(6);
        msg.obj = fan;
        handler.sendMessage(msg);
        Log.i("tag","run:已发送");
    }


    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "utf-8");
        int rsz;
        while ((rsz = in.read(buffer, 0, bufferSize)) >= 0) {
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}