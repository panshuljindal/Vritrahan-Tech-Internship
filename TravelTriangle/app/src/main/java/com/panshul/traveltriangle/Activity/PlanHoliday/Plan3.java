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

public class Plan3 extends AppCompatActivity {

    FormData data;
    ConstraintLayout star5,star4,star3,star2,star1,star0,flight,cab,next,back;
    boolean hotelSelected;
    TextView text5,text4,text3,text2,text1,text0;
    ImageView flightMain,flightTick,cabMain,cabTick;
    String hotel,inclusions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan3);
        hotelSelected = false;
        hotel = "null";
        inclusions="null";
        findViewByIds();
        onclickListeners();
    }
    public void onclickListeners(){
        ImageView cross = findViewById(R.id.imageViewPlan3);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text5.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text0.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                star5.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                star4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star0.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                hotel = "5 Star";
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));

            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text5.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text0.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                star4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                star5.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star0.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                hotel = "4 Star";
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));

            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text5.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text0.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                star3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                star4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star5.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star0.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                hotel = "3 Star";
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));

            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text5.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text0.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                star2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                star4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star5.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star0.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                hotel = "2 Star";
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));

            }
        });
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text5.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text0.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                star1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                star4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star5.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star0.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                hotel = "1 Star";
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));

            }
        });
        star0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text0.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                text4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                text5.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

                star0.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_selected));
                star4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                star5.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan3_unselected));
                hotel = "Hotel Not Required";
                next.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_button_background));

            }
        });
        flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan2_background_selected));
                cab.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan2_background));
                flightMain.setImageResource(R.drawable.ic_flight_selected);
                cabMain.setImageResource(R.drawable.ic_cab_unselected);
                cabTick.setVisibility(View.INVISIBLE);
                flightTick.setVisibility(View.VISIBLE);
                inclusions = "Flight";
            }
        });
        cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cab.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan2_background_selected));
                flight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plan2_background));
                flightMain.setImageResource(R.drawable.ic_flight_unselected);
                cabMain.setImageResource(R.drawable.ic_cab_selected);
                cabTick.setVisibility(View.VISIBLE);
                flightTick.setVisibility(View.INVISIBLE);
                inclusions = "Cab";
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hotel.equals("null")){

                }
                else {
                    loadData();
                    data.setHotel_category(hotel);
                    data.setOther_inclusions(inclusions);
                    saveData();
                    startActivity(new Intent(Plan3.this,Plan4.class));
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan3.super.onBackPressed();
            }
        });
    }
    public void findViewByIds(){
        star5 = findViewById(R.id.constraint5Star);
        star4 = findViewById(R.id.constraint4Star);
        star3 = findViewById(R.id.constraint3Star);
        star2 = findViewById(R.id.constraint2Star);
        star1 = findViewById(R.id.constraint1Star);
        star0 = findViewById(R.id.constraint0Star);
        flight = findViewById(R.id.flightsPlan3);
        cab = findViewById(R.id.cabsPlan3);
        next = findViewById(R.id.nextPlan3);
        back = findViewById(R.id.backPlan3);
        text5 = findViewById(R.id.text5Star);
        text4 = findViewById(R.id.text4Star);
        text3 = findViewById(R.id.text3Star);
        text2 = findViewById(R.id.text2Star);
        text1 = findViewById(R.id.text1Star);
        text0 = findViewById(R.id.text0Star);
        flightMain = findViewById(R.id.flightImageMain);
        flightTick = findViewById(R.id.flightImageTick);
        cabMain = findViewById(R.id.cabImageMain);
        cabTick = findViewById(R.id.cabImageTick);

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