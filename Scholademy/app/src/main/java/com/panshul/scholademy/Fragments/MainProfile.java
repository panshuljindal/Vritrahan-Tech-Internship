package com.panshul.scholademy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.scholademy.Activity.HomeScreen;
import com.panshul.scholademy.Activity.Login;
import com.panshul.scholademy.Model.Profile;
import com.panshul.scholademy.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainProfile extends Fragment {

    View view;
    TextView name,email,phone,age,exams,type,nameOfCoaching,courseName,fees,city,registerNcst,editProfile;
    String uid;
    Button logout;
    DatabaseReference myref;
    Profile profile;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_main_profile, container, false);
        findViewByIds();
        loadData();
        pref = view.getContext().getSharedPreferences("com.panshul.scholademy.profile",Context.MODE_PRIVATE);
        editor = pref.edit();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myref = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Profile");

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile = snapshot.getValue(Profile.class);
                setProfile();
                saveData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadData();
            }
        });
        editProfile.bringToFront();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragment = new ProfileFragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                clearData();
                Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        setProfile();
        return view;
    }
    void clearData(){
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.scholademy.userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();

        SharedPreferences pref1 = view.getContext().getSharedPreferences("com.panshul.scholademy.mst", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref1.edit();
        editor1.clear();
        editor1.apply();

    }
    void setProfile(){
        try {
            name.setText(profile.getName());
            email.setText(profile.getEmail().toLowerCase());
            phone.setText(profile.getPhone());
            age.setText(profile.getAge()+" years");
            String str = profile.getExams();
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
            exams.setText(finalList);
            type.setText(profile.getType());
            if (profile.getNameOfCoaching().equals("")){
                nameOfCoaching.setText("-");
            }
            else {
                nameOfCoaching.setText(profile.getNameOfCoaching());
            }
            if (profile.getCourseName().equals("")){
                courseName.setText("-");
            }
            else{
                courseName.setText(profile.getCourseName());
            }
            if (profile.getFees().equals("")){
                fees.setText("-");
            }
            else {
                fees.setText(profile.getFees());
            }
            if (profile.getCity().equals("")){
                city.setText("-");
            }
            else {
                city.setText(profile.getCity());
            }
            if (profile.getRegisterNcst().equals("Yes")){
                registerNcst.setText("Registered");
            }
            else {
                registerNcst.setText("Not Registered");
            }
        }
        catch (Exception e){
            name.setText("-");
            email.setText("-");
            phone.setText("-");
            age.setText("-");
            exams.setText("-");
            type.setText("-");
            nameOfCoaching.setText("-");
            courseName.setText("-");
            fees.setText("-");
            city.setText("-");
            registerNcst.setText("Not Registered");
        }


    }
    void findViewByIds(){
        name = view.findViewById(R.id.nameTextView);
        email = view.findViewById(R.id.emailTextView);
        phone = view.findViewById(R.id.phoneNumberTextView);
        age = view.findViewById(R.id.ageText);
        exams = view.findViewById(R.id.examsAppear);
        type = view.findViewById(R.id.preferenceTextView);
        nameOfCoaching = view.findViewById(R.id.coachingName);
        courseName = view.findViewById(R.id.courseNameCoaching);
        fees = view.findViewById(R.id.courseF);
        city = view.findViewById(R.id.cityText);

        registerNcst = view.findViewById(R.id.registerNcst);
        editProfile = view.findViewById(R.id.editProfile);

        logout  = view.findViewById(R.id.button3);
    }
    void saveData(){
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        editor.putString("profile",json);
        editor.apply();
    }
    void loadData(){
        try {
            String json1 = pref.getString("profile","");
            Type type = new TypeToken<Profile>() {}.getType();
            Gson gson = new Gson();
            profile = gson.fromJson(json1,type);
        }
        catch (Exception e){

        }

    }
}