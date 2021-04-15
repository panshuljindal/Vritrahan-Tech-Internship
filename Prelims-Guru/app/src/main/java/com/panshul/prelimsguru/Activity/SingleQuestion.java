package com.panshul.prelimsguru.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import com.panshul.prelimsguru.Model.Answers;
import com.panshul.prelimsguru.Model.QuestionModel;
import com.panshul.prelimsguru.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SingleQuestion extends AppCompatActivity {

    String test1,test2,test3,test4,uid;
    ArrayList<QuestionModel> finalQuestion;
    SharedPreferences pref,pref1;
    SharedPreferences.Editor edit;
    TextView timer,questionNumber,question,option1Text,option2Text,option3Text,option4Text;
    ImageView option1Image,option2Image,option3Image,option4Image;
    ArrayList<Answers> answers;
    Button next,previous,submit;
    ImageView singleToAll;
    int quesNo,maxQues;
    int quiztime = 600000;
    String option;
    public static long timeLeft;
    CountDownTimer countDownTimer;
    DatabaseReference myref;
    boolean cheat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);
        cheat=false;
        pref1 = getSharedPreferences("com.panshul.prelimsguru.final",MODE_PRIVATE);
        edit = pref1.edit();
        initialise();
        findViewByIds();
        maxQues = finalQuestion.size();
        quesNo = 0;
        try {
            Intent i = getIntent();
            String q = i.getStringExtra("quesNo");
            Log.i("quesNo",q);
            quesNo = Integer.valueOf(q);
        }catch (Exception e){
            Log.i("quesNo","Exception");
        }

        setOptions();
        setCountDownTimer();
        onclickListeners();
    }
    void onclickListeners(){

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quesNo++;
                if (quesNo<maxQues){
                    setOptions();
                }
                else {
                    quesNo--;
                    Toast.makeText(SingleQuestion.this, "No Questions Left", Toast.LENGTH_SHORT).show();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quesNo--;
                if (quesNo>=0){

                    setOptions();
                }
                else {
                    quesNo++;
                    Toast.makeText(SingleQuestion.this, "No Questions left", Toast.LENGTH_SHORT).show();
                }
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(SingleQuestion.this);
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
                for (int i = 0;i<answers.size();i++){
                    if (answers.get(i).getChoice().equals("null")){
                        unanswered1++;
                    }
                    if (answers.get(i).getChoice().equals(finalQuestion.get(i).getCorrect())){
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
                        edit.putString("answers","");
                        edit.putString("timeLeft","");
                        edit.apply();
                        countDownTimer.cancel();
                        Intent i= new Intent(SingleQuestion.this,ScoreShow.class);
                        i.putExtra("answered",String.valueOf(maxQues- finalUnanswered));
                        i.putExtra("unanswered",String.valueOf(finalUnanswered));
                        i.putExtra("score",String.valueOf(finalScore));
                        i.putExtra("timeLeft",String.valueOf(timeLeft));
                        startActivity(i);
                    }
                });
            }
        });
        option1Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1Image.setImageResource(R.drawable.ic_mcqselected);
                option2Image.setImageResource(R.drawable.ic_mcq);
                option3Image.setImageResource(R.drawable.ic_mcq);
                option4Image.setImageResource(R.drawable.ic_mcq);
                setAnswer(quesNo,option1Text.getText().toString());
            }
        });
        option2Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1Image.setImageResource(R.drawable.ic_mcq);
                option2Image.setImageResource(R.drawable.ic_mcqselected);
                option3Image.setImageResource(R.drawable.ic_mcq);
                option4Image.setImageResource(R.drawable.ic_mcq);
                setAnswer(quesNo,option2Text.getText().toString());
            }
        });
        option3Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1Image.setImageResource(R.drawable.ic_mcq);
                option2Image.setImageResource(R.drawable.ic_mcq);
                option3Image.setImageResource(R.drawable.ic_mcqselected);
                option4Image.setImageResource(R.drawable.ic_mcq);
                setAnswer(quesNo,option3Text.getText().toString());
            }
        });
        option4Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1Image.setImageResource(R.drawable.ic_mcq);
                option2Image.setImageResource(R.drawable.ic_mcq);
                option3Image.setImageResource(R.drawable.ic_mcq);
                option4Image.setImageResource(R.drawable.ic_mcqselected);
                setAnswer(quesNo,option4Text.getText().toString());
            }
        });

        singleToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                startActivity(new Intent(SingleQuestion.this,AllQuestion.class));
            }
        });
    }
    void setAnswer(int ques,String ans){
        answers.get(ques).setChoice(ans);
        for (int i=0;i<answers.size();i++){
            Log.i(answers.get(i).getQid(),answers.get(i).getChoice());
        }
    }
    void setCountDownTimer() {
        String hello = pref1.getString("timeLeft",String.valueOf(quiztime));
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
                for (int i = 0;i<answers.size();i++){
                    if (answers.get(i).getChoice().equals("null")){
                        unanswered3++;
                    }
                    if (answers.get(i).getChoice().equals(finalQuestion.get(i).getCorrect())){
                        score2++;
                    }
                }
                myref.setValue(String.valueOf(score2));
                edit.putString("answers","");
                edit.putString("timeLeft","");
                edit.apply();

                Intent i= new Intent(SingleQuestion.this,ScoreShow.class);
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
    public void setOptions(){
        if (quesNo<maxQues){
            if (quesNo>=0){
                questionNumber.setText("Question: "+String.valueOf(quesNo+1));
                question.setText(finalQuestion.get(quesNo).getQuestion());
                option1Text.setText(finalQuestion.get(quesNo).getOption1());
                option2Text.setText(finalQuestion.get(quesNo).getOption2());
                option3Text.setText(finalQuestion.get(quesNo).getOption3());
                option4Text.setText(finalQuestion.get(quesNo).getOption4());
                if (answers.get(quesNo).getChoice().equals("null")){
                    option1Image.setImageResource(R.drawable.ic_mcq);
                    option2Image.setImageResource(R.drawable.ic_mcq);
                    option3Image.setImageResource(R.drawable.ic_mcq);
                    option4Image.setImageResource(R.drawable.ic_mcq);
                }
                if (option1Text.getText().toString().equals(answers.get(quesNo).getChoice())){
                    option1Image.setImageResource(R.drawable.ic_mcqselected);
                    option2Image.setImageResource(R.drawable.ic_mcq);
                    option3Image.setImageResource(R.drawable.ic_mcq);
                    option4Image.setImageResource(R.drawable.ic_mcq);
                }
                if (option2Text.getText().toString().equals(answers.get(quesNo).getChoice())){
                    option1Image.setImageResource(R.drawable.ic_mcq);
                    option2Image.setImageResource(R.drawable.ic_mcqselected);
                    option3Image.setImageResource(R.drawable.ic_mcq);
                    option4Image.setImageResource(R.drawable.ic_mcq);
                }
                if (option3Text.getText().toString().equals(answers.get(quesNo).getChoice())){
                    option1Image.setImageResource(R.drawable.ic_mcq);
                    option2Image.setImageResource(R.drawable.ic_mcq);
                    option3Image.setImageResource(R.drawable.ic_mcqselected);
                    option4Image.setImageResource(R.drawable.ic_mcq);
                }
                if (option4Text.getText().toString().equals(answers.get(quesNo).getChoice())){
                    option1Image.setImageResource(R.drawable.ic_mcq);
                    option2Image.setImageResource(R.drawable.ic_mcq);
                    option3Image.setImageResource(R.drawable.ic_mcq);
                    option4Image.setImageResource(R.drawable.ic_mcqselected);
                }
            }
        }
        else {
           //submit();
        }
    }

    public void findViewByIds(){

        timer = findViewById(R.id.timerSingle);

        questionNumber = findViewById(R.id.questionNoSingle);
        question = findViewById(R.id.questionSingle);

        option1Text = findViewById(R.id.singleOption1Text);
        option1Image = findViewById(R.id.singleOption1Image);
        option2Text = findViewById(R.id.singleOption2Text);
        option2Image = findViewById(R.id.singleOption2Image);
        option3Text = findViewById(R.id.singleOption3Text);
        option3Image = findViewById(R.id.singleOption3Image);
        option4Text = findViewById(R.id.singleOption4Text);
        option4Image = findViewById(R.id.singleOption4Image);

        next = findViewById(R.id.singleNextButton);
        previous=findViewById(R.id.singlePreviousButton);
        submit = findViewById(R.id.singleSubmitButton);

        singleToAll = findViewById(R.id.singleToAll);
    }
    public void initialise(){
        option = "null";
        finalQuestion = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        pref = getSharedPreferences("com.panshul.prelimsguru.test", MODE_PRIVATE);
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        uid = user.getUid();
        test1 = pref.getString("bankTest1","");
        test2 = pref.getString("bankTest2","");
        test3 = pref.getString("railwayTest1","");
        test4 = pref.getString("railwayTest2","");
        String type = pref.getString("testType","");
        if (type.equals("test4")){
            loadData(test4);
            myref = db.getReference("Users").child(uid).child("Test").child("Railway").child("test2");
        }
        else if (type.equals("test3")){
            loadData(test3);
            myref = db.getReference("Users").child(uid).child("Test").child("Railway").child("test1");

        }
        else if (type.equals("test2")){
            loadData(test2);
            myref = db.getReference("Users").child(uid).child("Test").child("Bank").child("test2");

        }
        else if (type.equals("test1")){
            loadData(test1);
            myref = db.getReference("Users").child(uid).child("Test").child("Bank").child("test1");

        }
        answers = new ArrayList<>();
        for (int i=0;i<finalQuestion.size();i++){
            answers.add(new Answers(finalQuestion.get(i).getId(),"null"));
        }
        loadData1();
    }
    public void saveData(){
        SharedPreferences preferences = getSharedPreferences("com.panshul.prelimsguru.final",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson1 = new Gson();
        String json1 = gson1.toJson(finalQuestion);
        String json2 = gson1.toJson(answers);
        editor.putString("finalQuestions",json1);
        editor.putString("answers",json2);
        editor.apply();
    }
    public void loadData1(){
        SharedPreferences preferences1 = getSharedPreferences("com.panshul.prelimsguru.final",MODE_PRIVATE);
        String ques1 = preferences1.getString("answers","");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Answers>>() {}.getType();
        answers = gson.fromJson(ques1,type);
        if (answers==null){
            answers = new ArrayList<>();
            for (int i=0;i<finalQuestion.size();i++){
                answers.add(new Answers(finalQuestion.get(i).getId(),"null"));
            }
        }
    }
    public void loadData(String ques){

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<QuestionModel>>() {}.getType();
        finalQuestion = gson.fromJson(ques,type);
        if (finalQuestion==null){
            finalQuestion = new ArrayList<>();
        }
    }
    private boolean doubleback=false;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(doubleback){
            int unanswered2 = 0;
            int score1 = 0;
            for (int i = 0;i<answers.size();i++){
                if (answers.get(i).getChoice().equals("null")){
                    unanswered2++;
                }
                if (answers.get(i).getChoice().equals(finalQuestion.get(i).getCorrect())){
                    score1++;
                }
            }
            countDownTimer.cancel();
            myref.setValue(String.valueOf(score1));
            edit.putString("answers","");
            edit.putString("timeLeft","");
            edit.apply();
            Intent i= new Intent(SingleQuestion.this,ScoreShow.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (cheat){
            int unanswered3 = 0;
            int score2 = 0;
            for (int i = 0;i<answers.size();i++){
                if (answers.get(i).getChoice().equals("null")){
                    unanswered3++;
                }
                if (answers.get(i).getChoice().equals(finalQuestion.get(i).getCorrect())){
                    score2++;
                }
            }
            myref.setValue(String.valueOf(score2));
            edit.putString("answers","");
            edit.putString("timeLeft","");
            edit.apply();

            Intent i= new Intent(SingleQuestion.this,ScoreShow.class);
            i.putExtra("answered",String.valueOf(maxQues- unanswered3));
            i.putExtra("unanswered",String.valueOf(unanswered3));
            i.putExtra("score",String.valueOf(score2));
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