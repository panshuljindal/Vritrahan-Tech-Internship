package com.panshul.traveltriangle.Activity.PlanHoliday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.panshul.traveltriangle.Activity.MainActivity;
import com.panshul.traveltriangle.Model.FormModel.FormData;
import com.panshul.traveltriangle.R;

import java.util.UUID;

public class Plan1 extends AppCompatActivity {

    AutoCompleteTextView from,where;
    String[] cities = {"Delhi","Noida","Chennai","Mumbai","Indore","Kolkata","Punjab"};
    FormData data;
    ConstraintLayout next;
    boolean where1,from1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan1);
        ImageView cross = findViewById(R.id.imageViewPlan1);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        from = findViewById(R.id.fromTextView);
        where = findViewById(R.id.whereGoTextView);
        next = findViewById(R.id.nextPlan1);
        where1=false;
        from1 = false;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,cities);
        where.setThreshold(1);
        where.setAdapter(adapter);
        where.setTextColor(Color.BLACK);
        initializeForm();
        checkData();
        onclick();
    }
    private  void onclick(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (where1 && from1){
                    data.setTo(where.getText().toString());
                    data.setFrom(from.getText().toString());
                    saveData();
                    startActivity(new Intent(Plan1.this,Plan2.class));
                }
                else {

                }
            }
        });
    }
    private void initializeForm(){
        data = new FormData(UUID.randomUUID().toString()
                ,"null","null","null","null","null","null","null","null","null","null"
                ,"null","null","null","null","null","null","null","null");
        saveData();
    }
    public void saveData(){
        SharedPreferences pref = getSharedPreferences("com.panshul.traveltriangle.formData",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("formData",json);
        editor.apply();
    }
    public void checkData(){
        where.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (where.getText().toString().isEmpty()){
                    where1 = false;
                }
                else {
                    where1 = true;
                }
                if (from.getText().toString().isEmpty()){
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
                if (where.getText().toString().isEmpty()){
                    where1 = false;
                }
                else {
                    where1 = true;
                }
                if (from.getText().toString().isEmpty()){
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
        from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (where.getText().toString().isEmpty()){
                    where1 = false;
                }
                else {
                    where1 = true;
                }
                if (from.getText().toString().isEmpty()){
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
                if (where.getText().toString().isEmpty()){
                    where1 = false;
                }
                else {
                    where1 = true;
                }
                if (from.getText().toString().isEmpty()){
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


}