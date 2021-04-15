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

public class Bank extends AppCompatActivity {

    ImageView back;
    Button test1Button;
    Button test2Button;
    TextView test1Text,test2Text;
    DatabaseReference myref,myref1,myref2;
    List<QuestionModel> bankTest1,bankTest2;
    String test1,test2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        back = findViewById(R.id.backToMain);
        bankTest1 = new ArrayList<>();
        bankTest2 = new ArrayList<>();

        test1Button = findViewById(R.id.bankTest1Button);
        test1Text = findViewById(R.id.bankTest1Score);
        test2Button = findViewById(R.id.bankTest2Button);
        test2Text = findViewById(R.id.bankTest2Text);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseAuth mauth  = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        String uid = user.getUid() ;
        myref = db.getReference("Bank").child("test1");
        myref1 = db.getReference("Bank").child("test1");

        myref2 = db.getReference("Users").child(uid).child("Test").child("Bank");

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
                        bankTest1.clear();
                        bankTest2.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            QuestionModel model = ds.getValue(QuestionModel.class);
                            bankTest1.add(model);

                        }
                        test1Button.setEnabled(true);
                        test2Button.setEnabled(true);
                        saveData();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        test1Button.setEnabled(true);
                        test2Button.setEnabled(true);
                        Toast.makeText(Bank.this, "Please Try Again", Toast.LENGTH_SHORT).show();
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
                        bankTest2.clear();
                        bankTest1.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            QuestionModel model = ds.getValue(QuestionModel.class);
                            bankTest2.add(model);
                        }
                        test1Button.setEnabled(true);
                        test2Button.setEnabled(true);
                        saveData();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        test1Button.setEnabled(true);
                        test2Button.setEnabled(true);
                        Toast.makeText(Bank.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void saveData(){
        SharedPreferences preferences = getSharedPreferences("com.panshul.prelimsguru.test", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bankTest1);
        String json1 = gson.toJson(bankTest2);
        Intent intent = new Intent(Bank.this, Instructions.class);
        SharedPreferences preferences1 = getSharedPreferences("com.panshul.prelimsguru.final",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences1.edit();
        editor1.putString("answers","");
        editor1.putString("timeLeft","");
        editor1.apply();
        if (!bankTest1.isEmpty()){
            editor.putString("bankTest1",json);
            editor.putString("bankTest2","");
            editor.putString("testType","test1");

        }
        else if (!bankTest2.isEmpty()){

            editor.putString("bankTest2",json1);
            editor.putString("bankTest1","");
            editor.putString("testType","test2");

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