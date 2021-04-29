package com.panshul.scholademy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.panshul.scholademy.R;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView nav_view;
    DrawerLayout layout;
    Toolbar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        layout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.top_nav_view);
        bar = findViewById(R.id.my_awesome_toolbar);

        nav_view.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,layout,bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(this);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_syllabus:
                Toast.makeText(MainActivity.this, "Syllabus", Toast.LENGTH_SHORT).show();
                break;

        }
        layout.closeDrawer(GravityCompat.START);
        return true;
    }
}