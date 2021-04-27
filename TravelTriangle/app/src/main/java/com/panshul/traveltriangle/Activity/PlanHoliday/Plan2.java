package com.panshul.traveltriangle.Activity.PlanHoliday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.traveltriangle.Activity.MainActivity;
import com.panshul.traveltriangle.Model.FormModel.FormData;
import com.panshul.traveltriangle.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class Plan2 extends AppCompatActivity {

    CalendarView calendarView;
    String date;
    ConstraintLayout dD,dnD,dDCalendar,dnDCalendar,finalDate,next,back;
    ImageView dDImageMain,dnDImageMain,dnDImageTick,dDImageTick,plus,minus;
    TextView april,may,june,july,august,sep,afterSep,anytime,days,departureText;
    EditText departure;
    int noOfDays;
    FormData data;
    boolean depart,days1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan2);
        noOfDays = 1;
        depart = false;
        days1 = false;
        findViewByIds();
        calendarView = findViewById(R.id.calendarViewPlan1);
        //calendarView.setMinDate(System.currentTimeMillis());
        calendarView.setFocusedMonthDateColor(Color.BLACK);
        calendarView.bringToFront();
        onclick();
    }
    public void onclick(){
        ImageView cross = findViewById(R.id.imageViewPlan2);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        dD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depart = false;
                days1 = false;
                days.setText("Days");
                noOfDays=1;
                dDImageMain.setImageResource(R.drawable.ic_dd);
                dnDImageMain.setImageResource(R.drawable.ic_dnd);
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                dnDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.GONE);
                dDCalendar.setVisibility(View.VISIBLE);
                dnD.setBackground(ContextCompat.getDrawable(Plan2.this,R.drawable.signup_edittext) );
                dD.setBackground(ContextCompat.getDrawable(Plan2.this,R.drawable.plan2_background_selected));
                dnDImageTick.setVisibility(View.INVISIBLE);
                dDImageTick.setVisibility(View.VISIBLE);
            }
        });
        dnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depart = false;
                days1 = false;
                days.setText("Days");
                dDImageMain.setImageResource(R.drawable.ic_dnd);
                dnDImageMain.setImageResource(R.drawable.ic_dd);
                noOfDays=1;
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                dnDCalendar.setVisibility(View.VISIBLE);
                finalDate.setVisibility(View.GONE);
                dDCalendar.setVisibility(View.GONE);
                dD.setBackground(ContextCompat.getDrawable(Plan2.this,R.drawable.signup_edittext) );
                dnD.setBackground(ContextCompat.getDrawable(Plan2.this,R.drawable.plan2_background_selected));
                dDImageTick.setVisibility(View.INVISIBLE);
                dnDImageTick.setVisibility(View.VISIBLE);
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                dDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.VISIBLE);
                departure.setText(date);
                departureText.setText("Departure Date");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOfDays<=2){
                    days1 =false;
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grey_next));
                }
                else {
                    noOfDays--;
                    days1 = true;
                    days.setText(String.valueOf(noOfDays)+" Days");
                    next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));

                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOfDays++;
                days1 =true;
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));
                days.setText(String.valueOf(noOfDays)+" Days");
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (days1){
                    loadData();
                    if (departureText.getText().toString().equals("Departure Date")){
                        data.setDeparture_date(date);
                    }
                    else {
                        data.setDeparture_date(departure.getText().toString());
                    }
                    data.setNo_of_days(String.valueOf(noOfDays));
                    saveData();
                    startActivity(new Intent(Plan2.this,Plan3.class));
                }
                else {

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan2.super.onBackPressed();
            }
        });
        monthsOnclick();
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
    public void findViewByIds(){
        dD = findViewById(R.id.datesDecided);
        dnD = findViewById(R.id.datesNotDecided);
        dDCalendar= findViewById(R.id.dateDecidedCalender);
        dnDCalendar = findViewById(R.id.dateNotDecidedCalender);
        finalDate = findViewById(R.id.finalDatePlan2);
        dDImageMain = findViewById(R.id.dateDecidedImageMain);
        dDImageTick = findViewById(R.id.dateDecidedImageTick);
        dnDImageMain = findViewById(R.id.dateNotDecidedImageMain);
        dnDImageTick = findViewById(R.id.dateNotDecidedImageTick);
        april = findViewById(R.id.aprilTextView);
        may = findViewById(R.id.mayTextView);
        june = findViewById(R.id.juneTextView);
        july = findViewById(R.id.julyTextView);
        august = findViewById(R.id.augTextView);
        sep = findViewById(R.id.sepTextView);
        afterSep = findViewById(R.id.afterSepTextView);
        anytime = findViewById(R.id.anytimeTextView);
        days = findViewById(R.id.daysPlan2TextView);
        departureText = findViewById(R.id.departureTextPlan2);
        departure = findViewById(R.id.departureDateEditText);
        next = findViewById(R.id.nextPlan2);
        back = findViewById(R.id.backPlan2);
        plus = findViewById(R.id.plusPlan2);
        minus = findViewById(R.id.minusPlan2);
    }
    public void monthsOnclick(){
        april.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departureText.setText("Month of Travel");
                departure.setText("April");
                dnDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.VISIBLE);

            }
        });
        may.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departureText.setText("Month of Travel");
                departure.setText("May");
                dnDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.VISIBLE);

            }
        });
        june.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departureText.setText("Month of Travel");
                departure.setText("June");
                dnDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.VISIBLE);

            }
        });
        july.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departureText.setText("Month of Travel");
                departure.setText("July");
                dnDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.VISIBLE);

            }
        });
        august.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departureText.setText("Month of Travel");
                departure.setText("August");
                dnDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.VISIBLE);

            }
        });
        sep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departureText.setText("Month of Travel");
                departure.setText("September");
                dnDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.VISIBLE);

            }
        });
        afterSep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departureText.setText("Month of Travel");
                departure.setText("After Sep");
                dnDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.VISIBLE);

            }
        });
        anytime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departureText.setText("Month of Travel");
                departure.setText("Anytime");
                dnDCalendar.setVisibility(View.GONE);
                finalDate.setVisibility(View.VISIBLE);

            }
        });
    }
}