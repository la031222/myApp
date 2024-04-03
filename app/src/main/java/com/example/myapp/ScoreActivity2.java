package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapp.R;

public class ScoreActivity2 extends AppCompatActivity implements View.OnClickListener {
    //积分数组
    public int[] scoreArray = {1,2,3};
    public int old_score_a,old_score_b,new_score_a,new_score_b;
    public Button bta1,bta2,bta3,btb1,btb2,btb3,reset;
    public TextView text_a,text_b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score2);
        initView();//初始化
    };
    public void initView(){
        //获取按钮id
        bta1 = findViewById(R.id.buttona1);
        bta2 = findViewById(R.id.buttona2);
        bta3 = findViewById(R.id.buttona3);
        btb1 = findViewById(R.id.buttonb1);
        btb2 = findViewById(R.id.buttonb2);
        btb3 = findViewById(R.id.buttonb3);
        reset = findViewById(R.id.buttonreset);
        //得分清零
        text_a = findViewById(R.id.score_a);
        text_b = findViewById(R.id.score_b);
        //按钮监听
        btb1.setOnClickListener(this);
        btb2.setOnClickListener(this);
        btb3.setOnClickListener(this);
        bta1.setOnClickListener(this);
        bta2.setOnClickListener(this);
        bta3.setOnClickListener(this);
        reset.setOnClickListener(this);
    }
    private void reset(){
        text_a.setText("0");
        text_b.setText("0");
        new_score_a = 0;//a得分清零
        new_score_b = 0;//b得分清零
    }
    public void addScore(int tag,int score){
        if(tag == 0){//对a加分
            old_score_b = 0;
            old_score_a = score;
            new_score_a += old_score_a;
        }
        else if (tag == 1){//对b加分
            old_score_a = 0;
            old_score_b = score;
            new_score_b += old_score_b;
        }
        //显示最终分数
        text_a.setText(Integer.toString(new_score_a));
        text_b.setText(Integer.toString(new_score_b));
    }
    //点击事件
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.buttona1) addScore(0,scoreArray[0]);
        else if(v.getId() == R.id.buttona2) addScore(0,scoreArray[1]);
        else if(v.getId() == R.id.buttona3) addScore(0,scoreArray[2]);
        else if(v.getId() == R.id.buttonb1) addScore(1,scoreArray[0]);
        else if(v.getId() == R.id.buttonb2) addScore(1,scoreArray[1]);
        else if(v.getId() == R.id.buttonb3) addScore(1,scoreArray[2]);
        else if(v.getId() == R.id.buttonreset) reset();
    }

}
