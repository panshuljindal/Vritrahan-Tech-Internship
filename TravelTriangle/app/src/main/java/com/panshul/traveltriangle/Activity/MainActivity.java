package com.panshul.traveltriangle.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.panshul.traveltriangle.Fragment.HomeFragment;
import com.panshul.traveltriangle.Fragment.LoginFragment;
import com.panshul.traveltriangle.Fragment.MenuFragment;
import com.panshul.traveltriangle.Fragment.TripsFragment;
import com.panshul.traveltriangle.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

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
                    case R.id.navigation_trips:
                        selectedfragment = new LoginFragment();
                        break;
                    case R.id.navigation_menu:
                        selectedfragment = new MenuFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedfragment).commit();
                return  true;
            }
        });

    }
}