package com.panshul.scholademy.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.scholademy.Model.Answers;
import com.panshul.scholademy.Model.QuestionModel;
import com.panshul.scholademy.R;
import com.panshul.scholademy.Services.JavaMailAPI;
import com.panshul.scholademy.Services.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Quiz extends AppCompatActivity {

    String uid,type,questions;
    ArrayList<QuestionModel> finalQuestion;
    TextView timer,questionNumber,question,option1Text,option2Text,option3Text,option4Text,examType;
    ImageView option1Image,option2Image,option3Image,option4Image;
    ArrayList<Answers> answers;
    Button next,previous,submit;
    int quesNo,maxQues;
    int quiztime = 600000;
    String option;
    public static long timeLeft;
    CountDownTimer countDownTimer;
    DatabaseReference myref;
    SharedPreferences preferences;
    boolean cheat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        preferences = getSharedPreferences("com.panshul.scholademy.userdata",MODE_PRIVATE);
        cheat=false;
        findViewByIds();
        initialise();
        maxQues = finalQuestion.size();
        quesNo = 0;
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
                    Toast.makeText(Quiz.this, "No Questions Left", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Quiz.this, "No Questions left", Toast.LENGTH_SHORT).show();
                }
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Quiz.this);
                dialog.setContentView(R.layout.submitwarning);
                dialog.show();
                dialog.setCancelable(false);
                TextView answered,unanswered,timeLeftTextView;
                Button cancel,submitsubmit;
                answered = dialog.findViewById(R.id.attempted);
                timeLeftTextView = dialog.findViewById(R.id.timeLeft);
                unanswered = dialog.findViewById(R.id.unAttempted);
                cancel = dialog.findViewById(R.id.warningCancel);
                submitsubmit = dialog.findViewById(R.id.warningSubmit);
                int unanswered1 = 0;
                double score = 0;
                for (int i = 0;i<answers.size();i++){
                    if (answers.get(i).getChoice().equals("null")){
                        unanswered1++;
                    }
                    else {
                        score = score-0.25;
                    }
                    if (answers.get(i).getChoice().equals(finalQuestion.get(i).getCorrect())){
                        score++;
                    }
                }
                timeLeftTextView.setText(timer(timeLeft/1000));
                answered.setText(String.valueOf(maxQues-unanswered1));
                unanswered.setText(String.valueOf(unanswered1));
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                double finalScore = score;
                int finalUnanswered = unanswered1;
                submitsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countDownTimer.cancel();
                        myref.child("Answers").setValue(answers);
                        myref.child("Score").setValue(String.valueOf(finalScore));
                        String result="Name: "+preferences.getString("name","-")+"\n"
                                +"Email: "+preferences.getString("email","-") +"\n"
                                +"Phone Number: "+preferences.getString("phone","")+"\n"
                                +"User ID: " + FirebaseAuth.getInstance().getCurrentUser().getUid() +"\n"
                                +"Score: "+ String.valueOf(finalScore);
                        JavaMailAPI mailAPI2 = new JavaMailAPI(Quiz.this,Utils.sender,"Quiz Result "+examType.getText().toString(),result);
                        mailAPI2.execute();
                        Toast.makeText(Quiz.this, "Thanks for appearing in the test.", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(Quiz.this, HomeScreen.class);
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

    }
    String timer(long time){
        int min = (int) (time/60);
        int seconds = (int) (time-(min*60));
        String secondString;
        secondString = Integer.toString(seconds);
        if(seconds<=9){
            secondString="0"+secondString;
        }
        if(min>=1) {

             return (Integer.toString(min) + ":" + secondString +"s");
        }
        else{
            return ("00:"+secondString);
        }
    }
    void setAnswer(int ques,String ans){
        answers.get(ques).setChoice(ans);
    }
    void setCountDownTimer() {
        countDownTimer = new CountDownTimer(quiztime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTextView(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                int unanswered3 = 0;
                double score2 = 0;
                for (int i = 0;i<answers.size();i++){
                    if (answers.get(i).getChoice().equals("null")){
                        unanswered3++;
                    }else {
                        score2 = score2-0.25;
                    }
                    if (answers.get(i).getChoice().equals(finalQuestion.get(i).getCorrect())){
                        score2++;
                    }
                }
                myref.child("Answers").setValue(answers);
                myref.child("Score").setValue(String.valueOf(score2));
                String result="Name: "+preferences.getString("name","-")+"\n"
                        +"Email: "+preferences.getString("email","-") +"\n"
                        +"Phone Number: "+preferences.getString("phone","")+"\n"
                        +"User ID: " + FirebaseAuth.getInstance().getCurrentUser().getUid() +"\n"
                        +"Score: "+ String.valueOf(score2);

                JavaMailAPI mailAPI1 = new JavaMailAPI(Quiz.this, Utils.sender,"Quiz Result "+examType.getText().toString(),result);
                mailAPI1.execute();
                Toast.makeText(Quiz.this, "Thanks for appearing in the test!", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(Quiz.this,HomeScreen.class);
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

        examType = findViewById(R.id.quizHeading);

    }
    public void initialise(){
        option = "null";
        finalQuestion = new ArrayList<>();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        try {
            Intent intent = getIntent();
            type= intent.getStringExtra("type");
            questions = intent.getStringExtra("question");
        }
        catch (Exception e){
            Log.i("Exception",e.getMessage());
        }
        if (type.equals("type1")){
            myref = db.getReference("Users").child(uid).child("Exam").child("IITJEE");
            examType.setText("IIT JEE");
        }
        else if (type.equals("type2")){
            myref = db.getReference("Users").child(uid).child("Exam").child("UPSC");
            examType.setText("UPSC CSE GS");
        }
        else if (type.equals("type3")){
            myref = db.getReference("Users").child(uid).child("Exam").child("CLAT");
            examType.setText("CLAT");

        }
        else if (type.equals("type4")){
            myref = db.getReference("Users").child(uid).child("Exam").child("UGC");
            examType.setText("UGC NET");
        }

        else if (type.equals("type5")){
            examType.setText("CA/CS");
            myref = db.getReference("Users").child(uid).child("Exam").child("CA");
        }

        else if (type.equals("type6")){
            examType.setText("SSC, Bank PO, RBI, NABARD");
            myref = db.getReference("Users").child(uid).child("Exam").child("SSC");
        }

        loadData(questions);
        answers = new ArrayList<>();
        for (int i=0;i<finalQuestion.size();i++){
            answers.add(new Answers(finalQuestion.get(i).getId(),"null"));
        }
    }
    public void loadData(String ques){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<QuestionModel>>() {}.getType();
        finalQuestion =gson.fromJson(ques,type);
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
            double score1 = 0;
            for (int i = 0;i<answers.size();i++){
                if (answers.get(i).getChoice().equals("null")){
                    unanswered2++;
                }
                else {
                    score1 = score1-0.25;
                }
                if (answers.get(i).getChoice().equals(finalQuestion.get(i).getCorrect())){
                    score1++;
                }
            }
            countDownTimer.cancel();
            myref.child("Answers").setValue(answers);
            myref.child("Score").setValue(String.valueOf(score1));


            String result="Name: "+preferences.getString("name","-")+"\n"
                    +"Email: "+preferences.getString("email","-") +"\n"
                    +"Phone Number: "+preferences.getString("phone","")+"\n"
                    +"User ID: " + FirebaseAuth.getInstance().getCurrentUser().getUid() +"\n"
                    +"Score: "+ String.valueOf(score1);

            JavaMailAPI mailAPI2 = new JavaMailAPI(Quiz.this, Utils.sender,"Quiz Result "+examType.getText().toString(),result);
            mailAPI2.execute();
            Toast.makeText(this, "Thanks for appearing in the test!", Toast.LENGTH_SHORT).show();
            Intent i= new Intent(Quiz.this,HomeScreen.class);
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
            double score2 = 0;
            for (int i = 0;i<answers.size();i++){
                if (answers.get(i).getChoice().equals("null")){
                    unanswered3++;
                    score2 = score2-0.25;
                }
                if (answers.get(i).getChoice().equals(finalQuestion.get(i).getCorrect())){
                    score2++;
                }
            }
            myref.child("Answers").setValue(answers);
            myref.child("Score").setValue(String.valueOf(score2));


            String result="Name: "+preferences.getString("name","-")+"\n"
                    +"Email: "+preferences.getString("email","-") +"\n"
                    +"Phone Number: "+preferences.getString("phone","")+"\n"
                    +"User ID: " + FirebaseAuth.getInstance().getCurrentUser().getUid() +"\n"
                    +"Score: "+ String.valueOf(score2);

            JavaMailAPI mailAPI3 = new JavaMailAPI(Quiz.this, Utils.sender,"Quiz Result "+examType.getText().toString(),result);
            mailAPI3.execute();
            Toast.makeText(this, "Thanks for appearing in the test!", Toast.LENGTH_SHORT).show();
            Intent i= new Intent(Quiz.this,HomeScreen.class);
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cheat=true;
    }
}