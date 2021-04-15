package com.panshul.prelimsguru.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.prelimsguru.Adapter.AllQuestionAdapter;
import com.panshul.prelimsguru.Model.Answers;
import com.panshul.prelimsguru.Model.QuestionModel;
import com.panshul.prelimsguru.R;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.panshul.prelimsguru.Activity.SingleQuestion.timeLeft;

public class AllQuestion extends AppCompatActivity {

    SharedPreferences pref,pref1,pref2;
    SharedPreferences.Editor editor,edit;
    List<QuestionModel> questionsList;
    List<Answers> answersList;
    RecyclerView recyclerView;
    ImageView allToSingle;
    Button submit;
    DatabaseReference myref;
    int maxQues;
    int quiztime=600000;
    CountDownTimer countDownTimer;
    String uid;
    TextView timer;
    boolean cheat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_question);
        cheat=false;
        loadData();
        pref2 = getSharedPreferences("com.panshul.prelimsguru.final",MODE_PRIVATE);
        edit = pref2.edit();
        recyclerView = findViewById(R.id.allQuestionRecyclerView);
        AllQuestionAdapter adapter = new AllQuestionAdapter(questionsList,answersList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        setCountDownTimer();
        onclick();
    }
    void setCountDownTimer() {
        String hello = pref2.getString("timeLeft",String.valueOf(quiztime));
        Log.i("Hello",hello);
        if (hello.equals("600000")){
            quiztime = 600000;
        }
        else if(hello.equals("")){
            quiztime=600000;
        }
        else {
            quiztime = Integer.valueOf(hello);
        }
        countDownTimer = new CountDownTimer(quiztime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                edit.putString("timeLeft",String.valueOf(timeLeft));
                edit.apply();
                updateTextView(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                int unanswered3 = 0;
                int score2 = 0;
                for (int i = 0;i<answersList.size();i++){
                    if (answersList.get(i).getChoice().equals("null")){
                        unanswered3++;
                    }
                    if (answersList.get(i).getChoice().equals(questionsList.get(i).getCorrect())){
                        score2++;
                    }
                }
                myref.setValue(String.valueOf(score2));
                edit.putString("answers","");
                edit.putString("timeLeft","");
                edit.apply();

                Intent i= new Intent(AllQuestion.this,ScoreShow.class);
                i.putExtra("answered",String.valueOf(maxQues- unanswered3));
                i.putExtra("unanswered",String.valueOf(unanswered3));
                i.putExtra("score",String.valueOf(score2));
                i.putExtra("timeLeft",String.valueOf(timeLeft));
                startActivity(i);

            }
        }.start();
    }
    void updateTextView(long secondsLeft){
        int min = (int) (secondsLeft/60);
        int seconds = (int) (secondsLeft-(min*60));
        String secondString;
        secondString = Integer.toString(seconds);
        if(seconds<=9){
            secondString="0"+secondString;
        }
        if(min>=1) {
            timer.setText(Integer.toString(min) + ":" + secondString +"s");
        }
        else{
            timer.setText("00:"+secondString);
        }
    }
    public void loadData(){
        submit = findViewById(R.id.allQuestionSubmit);
        allToSingle = findViewById(R.id.allToSingle);
        timer = findViewById(R.id.timerAll);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        uid = user.getUid();
        pref1 = getSharedPreferences("com.panshul.prelimsguru.test", MODE_PRIVATE);
        String type = pref1.getString("testType","");
        if (type.equals("test4")){
            myref = db.getReference("Users").child(uid).child("Test").child("Railway").child("test2");
        }
        else if (type.equals("test3")){
            myref = db.getReference("Users").child(uid).child("Test").child("Railway").child("test1");

        }
        else if (type.equals("test2")){
            myref = db.getReference("Users").child(uid).child("Test").child("Bank").child("test2");

        }
        else if (type.equals("test1")){
            myref = db.getReference("Users").child(uid).child("Test").child("Bank").child("test1");
        }




        questionsList = new ArrayList<>();
        answersList = new ArrayList<>();
        pref = getSharedPreferences("com.panshul.prelimsguru.final",MODE_PRIVATE);
        editor = pref.edit();
        String ques = pref.getString("finalQuestions","");
        String ans = pref.getString("answers","");
        Type type2 = new TypeToken<ArrayList<QuestionModel>>() {}.getType();
        Type type1 = new TypeToken<ArrayList<Answers>>() {}.getType();
        Gson gson =new Gson();
        questionsList = gson.fromJson(ques,type2);
        if (questionsList==null){
            questionsList = new ArrayList<>();
        }
        answersList = gson.fromJson(ans,type1);
        if (answersList==null){
            answersList = new ArrayList<>();
        }
        maxQues = questionsList.size();
    }
    private boolean doubleback=false;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(doubleback){
            int unanswered2 = 0;
            int score1 = 0;
            for (int i = 0;i<answersList.size();i++){
                if (answersList.get(i).getChoice().equals("null")){
                    unanswered2++;
                }
                if (answersList.get(i).getChoice().equals(questionsList.get(i).getCorrect())){
                    score1++;
                }
            }
            myref.setValue(String.valueOf(score1));
            editor.putString("answers","");
            editor.apply();
            Intent i= new Intent(AllQuestion.this,ScoreShow.class);
            i.putExtra("answered",String.valueOf(maxQues- unanswered2));
            i.putExtra("unanswered",String.valueOf(unanswered2));
            i.putExtra("score",String.valueOf(score1));
            i.putExtra("timeLeft",String.valueOf(timeLeft));
            startActivity(i);
        }
        doubleback=true;
        Toast.makeText(this, "Test will be submitted if you press again", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleback=false;
            }
        },3000);
    }
    public void onclick(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(AllQuestion.this);
                dialog.setContentView(R.layout.submitwarning);
                dialog.show();
                dialog.setCancelable(false);
                TextView answered,unanswered;
                Button cancel,submitsubmit;
                answered = dialog.findViewById(R.id.warningAnswered);
                unanswered = dialog.findViewById(R.id.warningUnanswered);
                cancel = dialog.findViewById(R.id.warningCancel);
                submitsubmit = dialog.findViewById(R.id.warningSubmit);
                int unanswered1 = 0;
                int score = 0;
                for (int i = 0;i<answersList.size();i++){
                    if (answersList.get(i).getChoice().equals("null")){
                        unanswered1++;
                    }
                    if (answersList.get(i).getChoice().equals(questionsList.get(i).getCorrect())){
                        score++;
                    }
                }
                answered.setText("Answered: "+String.valueOf(maxQues-unanswered1));
                unanswered.setText("Unanswered: "+String.valueOf(unanswered1));
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                int finalScore = score;
                int finalUnanswered = unanswered1;
                submitsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myref.setValue(String.valueOf(finalScore));
                        editor.putString("answers","");
                        editor.apply();
                        Intent i= new Intent(AllQuestion.this,ScoreShow.class);
                        i.putExtra("answered",String.valueOf(maxQues- finalUnanswered));
                        i.putExtra("unanswered",String.valueOf(finalUnanswered));
                        i.putExtra("score",String.valueOf(finalScore));
                        i.putExtra("timeLeft",String.valueOf(timeLeft));
                        startActivity(i);
                    }
                });
            }
        });
        allToSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllQuestion.this,SingleQuestion.class));
            }
        });
    }
    public void saveData(){
        SharedPreferences preferences = getSharedPreferences("com.panshul.prelimsguru.final",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson1 = new Gson();
        String json2 = gson1.toJson(answersList);
        editor.putString("answers",json2);
        editor.apply();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (cheat){
            int unanswered2 = 0;
            int score1 = 0;
            for (int i = 0;i<answersList.size();i++){
                if (answersList.get(i).getChoice().equals("null")){
                    unanswered2++;
                }
                if (answersList.get(i).getChoice().equals(questionsList.get(i).getCorrect())){
                    score1++;
                }
            }
            myref.setValue(String.valueOf(score1));
            editor.putString("answers","");
            editor.apply();
            Intent i= new Intent(AllQuestion.this,ScoreShow.class);
            i.putExtra("answered",String.valueOf(maxQues- unanswered2));
            i.putExtra("unanswered",String.valueOf(unanswered2));
            i.putExtra("score",String.valueOf(score1));
            i.putExtra("timeLeft",String.valueOf(timeLeft));
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cheat=true;
    }
}