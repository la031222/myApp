package com.example.androidapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class RateList extends AppCompatActivity implements Runnable{
    Handler handler=new Handler(Objects.requireNonNull(Looper.myLooper())){
        @SuppressLint("SimpleDateFormat")
        @Override
        public void handleMessage(Message msg){
            ArrayList<HashMap<String,String>>rateList=(ArrayList<HashMap<String,String>>)msg.obj;
            String[]key={"name", "value"};
            int[]id={R.id.rateItem_name, R.id.rateItem_value};
            SimpleAdapter adapter=new SimpleAdapter(RateList.this,rateList,R.layout.rate_item,key,id);
            ((ListView)findViewById(R.id.rateList)).setAdapter(adapter);
            Toast.makeText(RateList.this, "数据已更新", Toast.LENGTH_SHORT).show();
            //更新数据库
            DBHelper dbHelper=new DBHelper(RateList.this);
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            db.execSQL("DELETE FROM RATE");
            for(HashMap<String,String>hashMap:rateList){
                ContentValues contentValues=new ContentValues();
                contentValues.put("NAME",hashMap.get("name"));
                contentValues.put("VALUE",hashMap.get("value"));
                db.insert("RATE",null,contentValues);
            }
            db.close();
            SharedPreferences.Editor editor=getSharedPreferences("rate_update_date",Activity.MODE_PRIVATE).edit();
            editor.putString("update_date",new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            editor.apply();
            super.handleMessage(msg);
        }
    };
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);
        String updateDate=getSharedPreferences("rate_update_date",MODE_PRIVATE).getString("update_date","");
        if(updateDate.equals(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))){
            //从数据库更新
            Log.i("RateList","从数据库更新");
            ArrayList<HashMap<String,String>>rateList=new ArrayList<>();
            DBHelper dbHelper=new DBHelper(RateList.this);
            SQLiteDatabase db=dbHelper.getReadableDatabase();
            String[]columns={"NAME", "VALUE"};
            @SuppressLint("Recycle") Cursor cursor=db.query("RATE",columns,null,null,null,null,null);
            while(cursor.moveToNext()){
                HashMap<String,String>hashMap=new HashMap<>();
                hashMap.put("name",cursor.getString(cursor.getColumnIndexOrThrow("NAME")));
                hashMap.put("value",cursor.getString(cursor.getColumnIndexOrThrow("VALUE")));
                rateList.add(hashMap);
            }
            db.close();
            Adapter adapter=new Adapter(this,R.layout.rate_item,rateList);
            ((ListView)findViewById(R.id.rateList)).setAdapter(adapter);
        }
        else{
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        try{
            Log.i("RateList","线程已启动");
            Elements as=Jsoup.connect("https://www.huilvzaixian.com").get().select("#app > ul > li.history-urls").first().getElementsByTag("a");
            ArrayList<HashMap<String,String>>rateList=new ArrayList<>();
            StringBuilder log= new StringBuilder();
            for(Element a:as){
                String[]s=a.text().split(" ");
                HashMap<String,String>hashMap;
                if(s.length==3){
                    hashMap=new HashMap<>();
                    hashMap.put("name",s[1]);
                    hashMap.put("value",s[2]);
                    rateList.add(hashMap);
                    log.append(s[1]).append("->").append(s[2]).append("\n");
                }
                else{
                    hashMap=new HashMap<>();
                    hashMap.put("name",s[1]+" "+s[2]);
                    hashMap.put("value",s[3]);
                    rateList.add(hashMap);
                    log.append(s[1]).append(" ").append(s[2]).append("->").append(s[3]).append("\n");
                }
            }
            Message message=handler.obtainMessage();
            message.obj=rateList;
            handler.sendMessage(message);
            Log.i("RateList",log.toString());
        }
        catch(IOException e){
            Toast.makeText(this, "获取数据失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }
}
class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context){
        super(context,"RATE",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE RATE(NAME TEXT PRIMARY KEY,VALUE TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
    }
}
class Adapter extends ArrayAdapter<HashMap<String,String>>{
    public Adapter(Context con, int resource, ArrayList<HashMap<String, String>> li) {
        super(con, resource, li);
    }
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {//重写方法
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rate_item, parent, false);
        }
        HashMap<String, String> hashMap=getItem(position);
        if(hashMap!=null){
            ((TextView) convertView.findViewById(R.id.rateItem_name)).setText(hashMap.get("name"));
            ((TextView) convertView.findViewById(R.id.rateItem_value)).setText(hashMap.get("value"));
        }
        return convertView;
    }
}