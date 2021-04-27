package com.panshul.traveltriangle.Activity.PlanHoliday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.traveltriangle.Activity.MainActivity;
import com.panshul.traveltriangle.Model.FormModel.FormData;
import com.panshul.traveltriangle.R;

import java.lang.reflect.Type;

public class Plan5 extends AppCompatActivity {
    FormData data;
    ConstraintLayout next,back;
    int noOfAdult,noOfChildren,noOfInfant;
    boolean child,adul,inf;
    TextView adult,children,infant;
    ImageView adultMinus,adultPlus,childrenMinus,childrenPlus,infantMinus,infantPlus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan5);
        noOfAdult = 0;
        noOfChildren=0;
        noOfInfant = 0;
        child = false;
        inf = false;
        adul =false;
        findViewByIds();
        onclickListeners();
    }
    public void onclickListeners(){

        ImageView cross = findViewById(R.id.imageViewPlan5);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        adultMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOfAdult==0){
                    adul = false;
                    if (child | inf | adul){
                        next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    }
                    else{
                        next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                    }
                }
                else if (noOfAdult>0){
                    noOfAdult--;
                    if (noOfAdult==0){
                        adul = false;
                        if (child | inf | adul){
                            next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                        }
                        else{
                            next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                        }
                        adult.setText(String.valueOf(noOfAdult)+" Adults");
                    }
                    else {
                        adul = true;
                        adult.setText(String.valueOf(noOfAdult)+" Adults");
                    }
                }
            }
        });
        adultPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOfAdult++;
                if (noOfAdult>=0){
                    adul = true;
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    adult.setText(String.valueOf(noOfAdult)+" Adults");
                }
            }
        });

        childrenMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOfChildren==0){
                    child = false;
                    if (child | inf | adul){
                        next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    }
                    else{
                        next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                    }
                }
                else if (noOfChildren>0){
                    noOfChildren--;
                    if (noOfChildren==0){
                        child = false;
                        if (child | inf | adul){
                            next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                        }
                        else{
                            next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                        }
                        children.setText(String.valueOf(noOfChildren)+" Children");
                    }
                    else {
                        child = true;
                        children.setText(String.valueOf(noOfChildren)+" Children");
                    }
                }
            }
        });
        childrenPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOfChildren++;
                if (noOfChildren>=0){
                    child = true;
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    children.setText(String.valueOf(noOfChildren)+" Children");
                }
            }
        });

        infantMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOfInfant==0){
                    inf = false;
                    if (child | inf | adul){
                        next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    }
                    else{
                        next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                    }
                }
                else if (noOfInfant>0){
                    noOfInfant--;
                    if (noOfInfant==0){
                        inf = false;
                        if (child | inf | adul){
                            next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                        }
                        else{
                            next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                        }
                        infant.setText(String.valueOf(noOfInfant)+" Infants");
                    }
                    else {
                        inf = true;
                        infant.setText(String.valueOf(noOfInfant)+" Infants");

                    }
                }
            }
        });
        infantPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOfInfant++;
                if (noOfInfant>=0){
                    inf = true;
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    infant.setText(String.valueOf(noOfInfant)+" Children");
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child | inf | adul){
                    loadData();
                    data.setAdults(String.valueOf(noOfAdult));
                    data.setChildren(String.valueOf(noOfChildren));
                    data.setInfant(String.valueOf(noOfInfant));
                    saveData();
                    startActivity(new Intent(Plan5.this,Plan6.class));
                }
                else{
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan5.super.onBackPressed();
            }
        });


    }
    public void findViewByIds(){
        adult = findViewById(R.id.adultsTextView);
        children = findViewById(R.id.childrenTextView);
        infant = findViewById(R.id.infantTextView);
        adultMinus = findViewById(R.id.minusAdultPlan5);
        adultPlus = findViewById(R.id.plusAdultPlan5);
        childrenMinus = findViewById(R.id.minusChildrenPlan5);
        childrenPlus = findViewById(R.id.plusChildrenPlan5);
        infantMinus = findViewById(R.id.minusInfantPlan5);
        infantPlus = findViewById(R.id.plusInfantPlan5);
        next = findViewById(R.id.nextPlan5);
        back = findViewById(R.id.backPlan5);
    }

    public void loadData(){
        SharedPreferences pref1 = getSharedPreferences("com.panshul.traveltriangle.formData",MODE_PRIVATE);
        String json1 = pref1.getString("formData","");
        Gson gson1 = new Gson();
        Type type = new TypeToken<FormData>() {}.getType();
        data = gson1.fromJson(json1,type);
    }
    public void saveData(){
        SharedPreferences pref = getSharedPreferences("com.panshul.traveltriangle.formData",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("formData",json);
        editor.apply();
    }
}