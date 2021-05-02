package com.panshul.scholademy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.panshul.scholademy.Activity.Quiz;
import com.panshul.scholademy.Model.QuestionModel;
import com.panshul.scholademy.R;

import java.util.ArrayList;
import java.util.List;

public class StartQuizActivity extends AppCompatActivity {

    View view;
    TextView questions,minutes;
    ImageView back;
    Button start;
    String type;
    ArrayList<QuestionModel> question;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        start = findViewById(R.id.startQuizButton);
        back = findViewById(R.id.backButtonInstructions);
        Intent intent = getIntent();
        questions = findViewById(R.id.totalQuestions);
        minutes = findViewById(R.id.totalMinutes);
        type=intent.getStringExtra("typeQuestion");
        //Log.i("type",type);
        if (type.equals("type1")){
            questions.setText("Total 50 MCQ's");
            minutes.setText("20 Minutes");
        }
        else if (type.equals("type2")){
            questions.setText("Total 50 MCQ's");
            minutes.setText("20 Minutes");
        }
        else if (type.equals("type3")){
            questions.setText("Total 50 MCQ's");
            minutes.setText("20 Minutes");
        }
        else if (type.equals("type4")){
            questions.setText("Total 50 MCQ's");
            minutes.setText("20 Minutes");
        }

        else if (type.equals("type5")){
            questions.setText("Total 50 MCQ's");
            minutes.setText("20 Minutes");
        }

        else if (type.equals("type6")){
            questions.setText("Total 50 MCQ's");
            minutes.setText("20 Minutes");
        }
        question = new ArrayList<>();
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Test").child(type);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(false);
                myref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        question.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            QuestionModel model = ds.getValue(QuestionModel.class);
                            question.add(model);
                        }
                        saveData();
                        start.setEnabled(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(view.getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                        start.setEnabled(true);
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void saveData(){
        Gson gson = new Gson();
        String json = gson.toJson(question);
        Intent intent = new Intent(StartQuizActivity.this, Quiz.class);

        intent.putExtra("question",json);
        //Log.i("type",type);
        intent.putExtra("type",type);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}