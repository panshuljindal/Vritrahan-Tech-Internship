package com.panshul.prelimsguru.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.panshul.prelimsguru.Fragments.BlankFragment;
import com.panshul.prelimsguru.Fragments.HomeFragment;
import com.panshul.prelimsguru.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    NavigationView nav_view;
    DrawerLayout layout;
    Toolbar bar;
    TextView name,email,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    case R.id.navigation_tests:
                        selectedfragment = new HomeFragment();
                        break;
                    case R.id.navigation_select:
                        selectedfragment = new BlankFragment();
                        break;
                    case R.id.navigation_courses:
                        selectedfragment = new BlankFragment();
                        break;
                    case R.id.navigation_doubts:
                        selectedfragment = new BlankFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedfragment).commit();
                return  true;
            }
        });

        layout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.top_nav_view);
        bar = findViewById(R.id.my_awesome_toolbar);

        nav_view.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,layout,bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(this);

        View headerView = nav_view.getHeaderView(0);
         name= (TextView) headerView.findViewById(R.id.headerTextName);
         email = headerView.findViewById(R.id.headerTextEmail);
         mobile = headerView.findViewById(R.id.headerTextPhone);
         SharedPreferences pref = getSharedPreferences("com.panshul.prelimsguru.userdata",MODE_PRIVATE);
         String name1 = pref.getString("name","");
         String email1 = pref.getString("email","");
         String phone1 = pref.getString("phone","");
         Log.i("User Info",name1+email1+phone1);
         name.setText(name1);
         email.setText(email1);
//         if (phone1.isEmpty()){
//             mobile.setVisibility(View.INVISIBLE);
//         }
//         else {
//             mobile.setVisibility(View.VISIBLE);
//             mobile.setText(phone1);
//         }

    }

    @Override
    protected void onStart() {
        if (getIntent().hasExtra("Bank Fragment")){
            Log.i("Fragment","Bank Fragment");
        }
        if (getIntent().hasExtra("Railway Fragment")){
            Log.i("Fragment","Railway Fragment");
        }
        super.onStart();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_syllabus:
                Toast.makeText(MainActivity.this, "Syllabus", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_contactus:
                Toast.makeText(MainActivity.this, "Contact Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_features:
                Toast.makeText(MainActivity.this, "Features", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_support:
                Toast.makeText(MainActivity.this, "Support", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_shareapp:
                Toast.makeText(MainActivity.this, "Share App", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_rateus:
                Toast.makeText(MainActivity.this, "Rate Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_privacy:
                Toast.makeText(MainActivity.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
                break;
        }
        layout.closeDrawer(GravityCompat.START);
        return true;
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if(layout.isDrawerOpen(GravityCompat.START)){
            layout.closeDrawer(GravityCompat.START);
        }
        else
        {
            if (doubleBackToExitPressedOnce) {
                //super.onBackPressed();
                super.onBackPressed();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

    }
}