package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TeamScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_score);
    }
    public void teamsScore_add(View v){
        if(v==findViewById(R.id.teamScore_add3A)||v==findViewById(R.id.teamScore_add2A)||v==findViewById(R.id.teamScore_add1A)){
            ((TextView) findViewById(R.id.teamScore_A)).setText(add(v,Integer.parseInt(((TextView)findViewById(R.id.teamScore_A)).getText().toString())));
        }
        else{
            ((TextView) findViewById(R.id.teamScore_B)).setText(add(v,Integer.parseInt(((TextView)findViewById(R.id.teamScore_B)).getText().toString())));
        }
    }
    private String add(View v,int score){
        if(v==findViewById(R.id.teamScore_add3A)||v==findViewById(R.id.teamScore_add3B)){
            return String.valueOf(score+3);
        }
        else if(v==findViewById(R.id.teamScore_add2A)||v==findViewById(R.id.teamScore_add2B)){
            return String.valueOf(score+2);
        }
        else{
            return String.valueOf(score+1);
        }
    }
    public void teamScore_reset(View v){
        ((TextView)findViewById(R.id.teamScore_A)).setText("0");
        ((TextView)findViewById(R.id.teamScore_B)).setText("0");
    }
}