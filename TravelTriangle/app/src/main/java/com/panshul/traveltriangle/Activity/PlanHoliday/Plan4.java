package com.panshul.traveltriangle.Activity.PlanHoliday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.traveltriangle.Activity.MainActivity;
import com.panshul.traveltriangle.Model.FormModel.FormData;
import com.panshul.traveltriangle.R;

import java.lang.reflect.Type;

public class Plan4 extends AppCompatActivity {
    FormData data;
    ImageView budget1Main,budget1Tick,budget2Main,budget2Tick,budget3Main,budget3Tick;
    ConstraintLayout budget1,budget2,budget3,next,back;
    String budget;
    EditText budgetEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan4);
        budget="null";
        findViewByIds();
        onclick();
    }
    public void onclick(){

        ImageView cross = findViewById(R.id.imageViewPlan4);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        budget1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budget1Tick.setVisibility(View.VISIBLE);
                budget2Tick.setVisibility(View.INVISIBLE);
                budget3Tick.setVisibility(View.INVISIBLE);

                budget = "Rs 17000-18000";

                budget1Main.setImageResource(R.drawable.ic_budget1_selected);
                budget2Main.setImageResource(R.drawable.ic_budget2_unselected);
                budget3Main.setImageResource(R.drawable.ic_budget3_unselected);

                budget1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan2_background_selected));
                budget2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));
                budget3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));

                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
            }
        });
        budget2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budget2Tick.setVisibility(View.VISIBLE);
                budget1Tick.setVisibility(View.INVISIBLE);
                budget3Tick.setVisibility(View.INVISIBLE);

                budget = "Rs 18000-20000";

                budget1Main.setImageResource(R.drawable.ic_budget1_unselected);
                budget2Main.setImageResource(R.drawable.ic_budget2_unselected);
                budget3Main.setImageResource(R.drawable.ic_budget3_unselected);

                budget2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan2_background_selected));
                budget1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));
                budget3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));

                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));

            }
        });
        budget3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budget3Tick.setVisibility(View.VISIBLE);
                budget2Tick.setVisibility(View.INVISIBLE);
                budget1Tick.setVisibility(View.INVISIBLE);

                budget = "Rs 20000-22000";

                budget1Main.setImageResource(R.drawable.ic_budget1_unselected);
                budget2Main.setImageResource(R.drawable.ic_budget2_unselected);
                budget3Main.setImageResource(R.drawable.ic_budget3_selected);

                budget3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan2_background_selected));
                budget2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));
                budget1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));

                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan4.super.onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                if (budget.length()>0){
                    data.setBudget(budget);
                    saveData();
                    startActivity(new Intent(Plan4.this,Plan5.class));
                }
                else {

                }
            }
        });
        budgetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (budget.equals("null")){
                    budget3Tick.setVisibility(View.INVISIBLE);
                    budget2Tick.setVisibility(View.INVISIBLE);
                    budget1Tick.setVisibility(View.INVISIBLE);

                    budget1Main.setImageResource(R.drawable.ic_budget1_unselected);
                    budget2Main.setImageResource(R.drawable.ic_budget2_unselected);
                    budget3Main.setImageResource(R.drawable.ic_budget3_unselected);

                    budget3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));
                    budget2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));
                    budget1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));

                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                }
                else {
                    budget = s.toString();
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (budget.isEmpty()){
                    budget3Tick.setVisibility(View.INVISIBLE);
                    budget2Tick.setVisibility(View.INVISIBLE);
                    budget1Tick.setVisibility(View.INVISIBLE);

                    budget1Main.setImageResource(R.drawable.ic_budget1_unselected);
                    budget2Main.setImageResource(R.drawable.ic_budget2_unselected);
                    budget3Main.setImageResource(R.drawable.ic_budget3_unselected);

                    budget3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));
                    budget2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));
                    budget1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_edittext));
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                }
                else {
                    budget = s.toString();
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });
    }
    public void findViewByIds(){
        budget1 = findViewById(R.id.budget1);
        budget1Main = findViewById(R.id.budget1Main);
        budget1Tick = findViewById(R.id.budget1Tick);

        budget2 = findViewById(R.id.budget2);
        budget2Main = findViewById(R.id.budget2Main);
        budget2Tick = findViewById(R.id.budget2Tick);

        budget3 = findViewById(R.id.budget3);
        budget3Main = findViewById(R.id.budget3Main);
        budget3Tick = findViewById(R.id.budget3Tick);

        budgetEditText = findViewById(R.id.budgetPlan4);

        back = findViewById(R.id.backPlan4);
        next = findViewById(R.id.nextPlan4);
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