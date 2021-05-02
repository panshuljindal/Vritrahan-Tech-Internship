package com.panshul.traveltriangle.Activity.PlanHoliday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.traveltriangle.Activity.MainActivity;
import com.panshul.traveltriangle.Model.FormModel.FormData;
import com.panshul.traveltriangle.R;

import java.lang.reflect.Type;

public class Plan6 extends AppCompatActivity {

    FormData data;
    ConstraintLayout next,back;
    AutoCompleteTextView email,phone;
    String email1,phone1;
    boolean where1,from1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan6);
        findViewByIds();
        SharedPreferences pref = getSharedPreferences("com.panshul.travel.userdata", Context.MODE_PRIVATE);
        email1 = pref.getString("email","");
        phone1 = pref.getString("phone","");
        email.setText(email1);
        phone.setText(phone1);
        where1=false;
        from1 = false;
        loadData();
        onclick();

    }
    public void onclick(){
        ImageView cross = findViewById(R.id.imageViewPlan6);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan6.super.onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (where1 && from1){
                    data.setEmail(email.getText().toString());
                    data.setPhone(phone.getText().toString());
                    saveData();
                    startActivity(new Intent(Plan6.this,Plan7.class));
                }
                else {

                }
            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (email.getText().toString().isEmpty()){
                    where1 = false;
                }
                else {
                    where1 = true;
                }
                if (phone.getText().toString().isEmpty()){
                    from1 =false;
                }
                else {
                    from1=true;
                }
                if (where1 && from1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    //Log.i("Button","ON");
                }
                else {
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                    //Log.i("Button","Off");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (email.getText().toString().isEmpty()){
                    where1 = false;
                }
                else {
                    where1 = true;
                }
                if (phone.getText().toString().isEmpty()){
                    from1 =false;
                }
                else {
                    from1=true;
                }
                if (where1 && from1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    //Log.i("Button","ON");
                }
                else {
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                    //Log.i("Button","Off");
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (email.getText().toString().isEmpty()){
                    where1 = false;
                }
                else {
                    where1 = true;
                }
                if (phone.getText().toString().isEmpty()){
                    from1 =false;
                }
                else {
                    from1=true;
                }
                if (where1 && from1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    //Log.i("Button","ON");
                }
                else {
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                    //Log.i("Button","Off");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (email.getText().toString().isEmpty()){
                    where1 = false;
                }
                else {
                    where1 = true;
                }
                if (phone.getText().toString().isEmpty()){
                    from1 =false;
                }
                else {
                    from1=true;
                }
                if (where1 && from1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                    //Log.i("Button","ON");
                }
                else {
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                    //Log.i("Button","Off");
                }
            }
        });
    }
    public void findViewByIds(){
        next = findViewById(R.id.nextPlan6);
        back = findViewById(R.id.backPlan6);

        email = findViewById(R.id.emailIdPlan6);
        phone  = findViewById(R.id.phoneNumberPlan6);
    }
    public void loadData(){
        SharedPreferences pref1 = getSharedPreferences("com.panshul.traveltriangle.formData",MODE_PRIVATE);
        String json1 = pref1.getString("formData","");
        Log.i("formData",json1);
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