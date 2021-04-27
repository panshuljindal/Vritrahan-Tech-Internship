package com.panshul.traveltriangle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.traveltriangle.Model.FormModel.FormData;
import com.panshul.traveltriangle.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreferenceActivity extends AppCompatActivity {

    FormData data;
    ImageView back;
    TextView destination,departureCity,departureText,departureDate
            ,duration,travelers,hotel,inclusions,budget,additional
            ,iWillBook,typeOfPackage,localCab,localExperiences,emailID,phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_preference);

        loadData();
        findViewByIds();
        onclick();
        setData();
    }
    public void setData(){
        destination.setText(data.getTo());
        departureCity.setText(data.getFrom());
        if (data.getMonth().equals("null")){
            departureText.setText("Departure Date");
            departureDate.setText(data.getDeparture_date());
        }
        else {
            departureText.setText("Departure Month");
            departureDate.setText(data.getMonth());
        }
        int days = Integer.valueOf(data.getNo_of_days());
        duration.setText(String.valueOf(days) +" days & " +String.valueOf(days-1) +" nights");
        String people="",adult1="",children1="",infant1="";
        if (!data.getAdults().equals("0")){
            adult1 = people+data.getAdults()+" Adults ";
        }
        if (!data.getChildren().equals("0")){
            children1 = people+data.getChildren()+ " Children ";
        }
        if (!data.getInfant().equals("0")){
            infant1 = people+data.getInfant() +" Infants";
        }
        people=adult1+children1+infant1;
        travelers.setText(people);
        hotel.setText(data.getHotel_category());
        inclusions.setText(data.getOther_inclusions());
        budget.setText(data.getBudget());
        iWillBook.setText(data.getWillBook());
        if (data.getOther_inclusions().contains("Cab")){
            localCab.setText("Yes");
        }
        else {
            localCab.setText("-");
        }
        if (data.getLocalExperiences().equals("null")){
            localExperiences.setText("-");
        }
        else {
            String str = data.getLocalExperiences();
            int c =0;
            ArrayList<String> names = new ArrayList<>();
            String str2 = "";
            str=str+"/";
            String finalList="";
            String str1 = str.substring(1,str.length());
            for(int i =0; i<str1.length();i++)
            {
                char ch = str1.charAt(i);

                str2 = str2 + ch;
                c++;
                if(ch == '/')
                {
                    int n = str2.length();
                    str2 = str2.substring(0,c-1);
                    names.add(str2);
                    c =0;
                    str2 ="";
                }
            }
            for (int i=0;i<names.size();i++){
                if (i==names.size()-1){
                    finalList = finalList+names.get(i)+" ";

                }
                else {
                    finalList = finalList+names.get(i)+", ";

                }
            }
            localExperiences.setText(finalList);
        }
        emailID.setText(data.getEmail());
        phoneNumber.setText(data.getPhone());
    }
    public void onclick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceActivity.super.onBackPressed();
            }
        });
    }
    public void findViewByIds(){
        back = findViewById(R.id.backImageViewPreference);

        destination = findViewById(R.id.destinationtextView);
        departureCity = findViewById(R.id.departureCity);
        departureText = findViewById(R.id.departureDateText);
        departureDate = findViewById(R.id.departureTextViewP);
        duration = findViewById(R.id.durationTextView);
        travelers = findViewById(R.id.travelersTextView);
        hotel = findViewById(R.id.hotelsTextView);
        inclusions = findViewById(R.id.inclusionsTextView);
        budget = findViewById(R.id.budgetTextView);
        additional = findViewById(R.id.additionalTextView);
        iWillBook = findViewById(R.id.iWillBookTextView);
        typeOfPackage = findViewById(R.id.typeOfPackageTextView);
        localCab = findViewById(R.id.cabForSightseeingTextView);
        localExperiences = findViewById(R.id.localExpTextView);
        emailID = findViewById(R.id.emailIdP);
        phoneNumber = findViewById(R.id.phoneP);
    }
    public void loadData(){
        Intent intent = getIntent();
        String json = intent.getStringExtra("modelActivity");
        //Log.i("Model",json);
        Gson gson1 = new Gson();
        Type type = new TypeToken<FormData>() {}.getType();
        data = gson1.fromJson(json,type);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}