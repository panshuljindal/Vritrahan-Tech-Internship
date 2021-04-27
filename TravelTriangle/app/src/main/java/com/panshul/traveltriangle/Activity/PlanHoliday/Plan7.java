package com.panshul.traveltriangle.Activity.PlanHoliday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.traveltriangle.Activity.MainActivity;
import com.panshul.traveltriangle.Model.FormModel.FormData;
import com.panshul.traveltriangle.R;

import java.lang.reflect.Type;

public class    Plan7 extends AppCompatActivity {

    FormData data;
    ConstraintLayout next,back,next23Days,thisWeek,thisMonth,laterSometime,checkingPrice,cl1825,cl2635,cl3640,cl4150,cl50;
    TextView textNext23Days,textThisWeek,textThisMonth,textLaterSometime,textCheckingPrice,text1825,text2635,text3640,text4150,text50;
    String age,book,local;
    boolean age1,book1;
    TextView clearBook,clearAge,clearExp;
    CheckBox check1,check2,check3,check4,check5,check6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan7);
        findViewByIds();
        local="null";
        age ="null";
        book = "null";
        onclick();
    }
    public void OnCheckBoxClicked(View view){

        local="";
        if (check1.isChecked()){
            local = local+"/Delhi Heart";
        }
        if (check2.isChecked()){
            local = local+"/RedFort";
        }
        if (check3.isChecked()) {
            local = local + "/Chandni Chowk";
        }
        if (check4.isChecked()) {
            local = local+"/Qutub Minar";
        }
        if (check5.isChecked()){
            local = local+"/Akshardham";
        }
        if (check6.isChecked()){
            local = local+"/Jama Masjid";
        }
        //Log.i("Local",local);

    }
    public void onclick(){
        ImageView cross = findViewById(R.id.imageViewPlan7);
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
                Plan7.super.onBackPressed();
            }
        });
        next23Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNext23Days.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                textThisWeek.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisMonth.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textLaterSometime.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textCheckingPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                next23Days.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                thisWeek.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisMonth.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                laterSometime.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                checkingPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                book1 = true;
                book=textNext23Days.getText().toString();
                if (age1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });

        thisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNext23Days.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisWeek.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                textThisMonth.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textLaterSometime.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textCheckingPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                next23Days.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisWeek.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                thisMonth.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                laterSometime.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                checkingPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                book1 = true;
                book=textThisWeek.getText().toString();
                if (age1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });
        thisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNext23Days.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisWeek.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisMonth.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                textLaterSometime.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textCheckingPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                next23Days.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisWeek.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisMonth.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                laterSometime.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                checkingPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                book1 = true;
                book=textThisMonth.getText().toString();
                if (age1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });
        laterSometime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNext23Days.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisWeek.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisMonth.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textLaterSometime.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                textCheckingPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                next23Days.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisWeek.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisMonth.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                laterSometime.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                checkingPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                book1 = true;
                book=textLaterSometime.getText().toString();
                if (age1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });
        checkingPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNext23Days.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisWeek.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisMonth.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textLaterSometime.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textCheckingPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));

                next23Days.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisWeek.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisMonth.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                laterSometime.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                checkingPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                book1 = true;
                book=textCheckingPrice.getText().toString();
                if (age1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });
        clearBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNext23Days.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisWeek.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textThisMonth.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textLaterSometime.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                textCheckingPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                next23Days.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisWeek.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                thisMonth.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                laterSometime.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                checkingPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                book1 = false;
                book="null";
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
            }
        });
        cl1825.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1825.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text2635.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3640.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text4150.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text50.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                cl1825.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                cl2635.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl3640 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl4150 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl50 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));

                age = text1825.getText().toString();
                age1 = true;
                if (book1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });

        cl2635.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1825.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2635.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text3640.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text4150.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text50.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                cl1825.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl2635.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                cl3640 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl4150 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl50 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));

                age = text2635.getText().toString();
                age1 = true;
                if (book1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });
        cl3640.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1825.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2635.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3640.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text4150.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text50.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                cl1825.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl2635.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl3640 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                cl4150 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl50 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));

                age = text3640.getText().toString();
                age1 = true;
                if (book1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });
        cl4150.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1825.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2635.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3640.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text4150.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text50.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                cl1825.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl2635.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl3640 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl4150 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                cl50 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));

                age = text4150.getText().toString();
                age1 = true;
                if (book1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });
        cl50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1825.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2635.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3640.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text4150.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text50.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));

                cl1825.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl2635.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl3640 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl4150 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl50 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));

                age = text50.getText().toString();
                age1 = true;
                if (book1){
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                }
            }
        });

        clearAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1825.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2635.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3640.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text4150.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text50.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                cl1825.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl2635.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl3640 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl4150 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                cl50 .setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));

                age = "null";
                age1 = false;
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));

            }
        });

        clearExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check1.setChecked(false);
                check2.setChecked(false);
                check3.setChecked(false);
                check4.setChecked(false);
                check5.setChecked(false);
                check6.setChecked(false);
                local="null";
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (age1 && book1){
                    loadData();
                    data.setWillBook(book);
                    data.setAge(age);
                    data.setLocalExperiences(local);
                    sendData();

                }
                else {
                }
            }
        });
    }
    public void sendData(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myref = db.getReference("Users");
        try {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            myref.child(uid).child("Booking").child(data.getId()).setValue(data);
            saveData();
            Toast.makeText(this, "Trip Planned Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Plan7.this,MainActivity.class));
        }
        catch (Exception e){
            Toast.makeText(this, "Please Login Before Planning Trip", Toast.LENGTH_SHORT).show();
        }

    }
    public void findViewByIds(){
        next = findViewById(R.id.nextPlan7);
        back = findViewById(R.id.backPlan7);

        next23Days = findViewById(R.id.next23Days);
        thisWeek = findViewById(R.id.thisWeek);
        thisMonth = findViewById(R.id.thisMonth);
        laterSometime = findViewById(R.id.laterSometime);
        checkingPrice = findViewById(R.id.checkingPrice);
        cl1825 = findViewById(R.id.cl1825);
        cl2635 = findViewById(R.id.cl2635);
        cl3640 = findViewById(R.id.cl3640);
        cl4150 = findViewById(R.id.cl4150);
        cl50 = findViewById(R.id.cl50);

        textNext23Days=findViewById(R.id.textNext23Days);
        textThisWeek  =findViewById(R.id.textThisWeek);
        textThisMonth = findViewById(R.id.textThisMonth);
        textLaterSometime = findViewById(R.id.textLaterSometime);
        textCheckingPrice = findViewById(R.id.textcheckingPrice);
        text1825 = findViewById(R.id.text1825);
        text2635 = findViewById(R.id.text2635);
        text3640  = findViewById(R.id.text3640);
        text4150 = findViewById(R.id.text4150);
        text50 = findViewById(R.id.text50);

        clearAge = findViewById(R.id.ageClear);
        clearBook=findViewById(R.id.bookClear);
        clearExp = findViewById(R.id.experienceClear);

        check1=findViewById(R.id.checkBoxDelhiHart);
        check2 = findViewById(R.id.checkBoxRedFort);
        check3 = findViewById(R.id.checkBoxChandniChowk);
        check4 = findViewById(R.id.checkBoxQutubMinar);
        check5 = findViewById(R.id.checkBoxAkshardham);
        check6 =findViewById(R.id.checkBoxJamaMasjid);
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
        editor.putString("formData","");
        editor.apply();
    }
}