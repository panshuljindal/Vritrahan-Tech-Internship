package com.panshul.prelimsguru.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.panshul.prelimsguru.R;

public class ScoreShow extends AppCompatActivity {

    TextView wrong,correct,skipped,accuracy,totalTime,speed,marks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_show);

        wrong = findViewById(R.id.wrongTextView);
        correct = findViewById(R.id.correcttextview);
        skipped = findViewById(R.id.skippedTextView);
        accuracy = findViewById(R.id.accuracy);
        totalTime = findViewById(R.id.totalTime);
        speed = findViewById(R.id.speed);
        marks = findViewById(R.id.marks);

        Intent i = getIntent();
        int score = Integer.valueOf(i.getStringExtra("score"));
        int answered = Integer.valueOf(i.getStringExtra("answered"));
        int unanswered = Integer.valueOf(i.getStringExtra("unanswered"));
        int timeLeft = Integer.valueOf(i.getStringExtra("timeLeft"));

        //Log.i("Answers",String.valueOf(score)+ String.valueOf(answered)+ String.valueOf(unanswered)+ String.valueOf(timeLeft));
        int total = unanswered+answered;
        wrong.setText(String.valueOf(answered-score));
        correct.setText(String.valueOf(score));
        skipped.setText(String.valueOf(unanswered));
        accuracy.setText(String.valueOf(score*100/total)+"%");
        totalTime.setText(String.valueOf((600000-timeLeft)/1000/60)+" Min "+((600000-timeLeft)/1000%60)+"s");
        if (answered==0){
            speed.setText("0 Q/Min");
        }
        else {
            if ((600000-timeLeft)/1000/60==0){
                speed.setText(String.valueOf(answered)+" Q/Min");
            }
            else {
                speed.setText(String.valueOf(answered/(600000-timeLeft)/1000/60)+" Q/Min");

            }
            //Log.i("timeLeft",String.valueOf(timeLeft));
        }
        marks.setText(String.valueOf(score)+"/"+String.valueOf(total));




        Button home = findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScoreShow.this,MainActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(ScoreShow.this,MainActivity.class));
    }
}