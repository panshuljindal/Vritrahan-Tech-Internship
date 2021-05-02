package com.panshul.traveltriangle.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.panshul.traveltriangle.Fragment.BookingFragment;
import com.panshul.traveltriangle.Fragment.HomeFragment;
import com.panshul.traveltriangle.Fragment.LoginFragment;
import com.panshul.traveltriangle.Fragment.MenuFragment;
import com.panshul.traveltriangle.Model.UserData;
import com.panshul.traveltriangle.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myref = FirebaseDatabase.getInstance().getReference("Users");
        bottomNavigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment = null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        selectedfragment = new HomeFragment();
                        break;
                    case R.id.navigation_trips:
                        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                            selectedfragment = new BookingFragment();
                        }else {
                            selectedfragment = new LoginFragment();
                        }
                        break;
                    case R.id.navigation_menu:
                        selectedfragment = new MenuFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedfragment).commit();
                return  true;
            }
        });
        saveData();
    }
    public void saveData(){
        try {


            FirebaseAuth mauth = FirebaseAuth.getInstance();
            String uid = mauth.getCurrentUser().getUid();
            myref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        UserData user = snapshot.child(uid).getValue(UserData.class);
                        SharedPreferences pref = getSharedPreferences("com.panshul.travel.userdata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("name", user.getName());
                        editor.putString("uid", user.getUid());
                        editor.putString("email", user.getEmail());
                        editor.putString("phone", user.getPhone());
                        editor.apply();
                    } catch (NullPointerException e) {
                        Toast.makeText(MainActivity.this, "Please SignUp Before Login", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){

        }
    }
    Boolean doubleback=false;
    @Override
    public void onBackPressed() {
        if(doubleback) {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } else {
            doubleback = true;

            Toast.makeText(this, "Please BACK once again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleback = false;
                }
            }, 2000);
        }
    }
}