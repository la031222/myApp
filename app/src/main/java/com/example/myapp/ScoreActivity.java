package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {


    private int score = 0;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreTextView = findViewById(R.id.out0);

        Button addOneButton = findViewById(R.id.out1);
        addOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score += 1;
                updateScoreDisplay();
            }
        });

        Button addTwoButton = findViewById(R.id.out2);
        addTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score += 2;
                updateScoreDisplay();
            }
        });

        Button addThreeButton = findViewById(R.id.out3);
        addThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score += 3;
                updateScoreDisplay();
            }
        });

        Button resetButton = findViewById(R.id.out);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                updateScoreDisplay();
            }
        });
    }

    private void updateScoreDisplay() {
        scoreTextView.setText("当前分数： " + score);
    }
}