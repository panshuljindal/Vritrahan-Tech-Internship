package com.panshul.prelimsguru.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.panshul.prelimsguru.Model.QuestionModel;
import com.panshul.prelimsguru.R;

import java.util.ArrayList;
import java.util.List;

public class Railway extends AppCompatActivity {

    ImageView back;
    Button test1Button;
    Button test2Button;
    TextView test1Text,test2Text;
    DatabaseReference myref,myref1,myref2;
    List<QuestionModel> railwayTest1,railwayTest2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway);
        back = findViewById(R.id.backToMainRailway);
        railwayTest1 = new ArrayList<>();
        railwayTest2 = new ArrayList<>();

        test1Button = findViewById(R.id.railwayTest1Button);
        test1Text = findViewById(R.id.railwayTest1Score);
        test2Button = findViewById(R.id.railwayTest2Button);
        test2Text = findViewById(R.id.railwayTest2Text);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseAuth mauth  = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        String uid = user.getUid();
        myref = db.getReference("Bank").child("test1");
        myref1 = db.getReference("Bank").child("test1");
        myref2 = db.getReference("Users").child(uid).child("Test").child("Railway");

        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                test1Text.setText(snapshot.child("test1").getValue().toString());
                test2Text.setText(snapshot.child("test2").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        onclickListeners();
    }
    public void onclickListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        test1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1Button.setEnabled(false);
                test2Button.setEnabled(false);
                myref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        railwayTest1.clear();
                        railwayTest2.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            QuestionModel model = ds.getValue(QuestionModel.class);
                            railwayTest1.add(model);

                        }
                        test1Button.setEnabled(true);
                        test2Button.setEnabled(true);
                        saveData();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        test1Button.setEnabled(true);
                        test2Button.setEnabled(true);
                        Toast.makeText(Railway.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        test2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1Button.setEnabled(false);
                test2Button.setEnabled(false);
                myref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        railwayTest2.clear();
                        railwayTest1.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            QuestionModel model = ds.getValue(QuestionModel.class);
                            railwayTest2.add(model);
                        }
                        test1Button.setEnabled(true);
                        test2Button.setEnabled(true);
                        saveData();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        test1Button.setEnabled(true);
                        test2Button.setEnabled(true);
                        Toast.makeText(Railway.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void saveData(){
        SharedPreferences preferences = getSharedPreferences("com.panshul.prelimsguru.test", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(railwayTest1);
        String json1 = gson.toJson(railwayTest2);
        Intent intent = new Intent(Railway.this, Instructions.class);
        SharedPreferences preferences1 = getSharedPreferences("com.panshul.prelimsguru.final",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences1.edit();
        editor1.putString("answers","");
        editor1.putString("timeLeft","");
        editor1.apply();
        if (railwayTest2.isEmpty()){
            editor.putString("railwayTest1",json);
            editor.putString("railwayTest2","");
            editor.putString("testType","test3");

        }
        else if (railwayTest1.isEmpty()){
            editor.putString("railwayTest1","");
            editor.putString("railwayTest2",json1);
            editor.putString("testType","test4");


        }
        editor.apply();
        startActivity(intent);
    }
//    public void loadData(){
//        SharedPreferences preferences = getSharedPreferences("com.panshul.prelimsguru.test", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = preferences.getString("scrollbar","");
//        String json1 = preferences.getString("notifications","");
//        Type type = new TypeToken<ArrayList<card1item>>() {}.getType();
//        Type type1 = new TypeToken<ArrayList<card2item>>() {}.getType();
//    }
}