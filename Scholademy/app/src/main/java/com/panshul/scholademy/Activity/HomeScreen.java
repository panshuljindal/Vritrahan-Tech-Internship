package com.panshul.scholademy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.panshul.scholademy.Fragments.AboutUsFragment;
import com.panshul.scholademy.Fragments.ContactFragment;
import com.panshul.scholademy.Fragments.MstFragment;
import com.panshul.scholademy.Fragments.NcstFragment;
import com.panshul.scholademy.Fragments.ProfileFragment;
import com.panshul.scholademy.Fragments.NbsFragment;
import com.panshul.scholademy.Fragments.MainProfile;
import com.panshul.scholademy.R;
import com.panshul.scholademy.Fragments.FaqFragment;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView nav_view;
    DrawerLayout layout;
    ImageView profile;
    Toolbar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        layout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.top_nav_view);
        bar = findViewById(R.id.my_awesome_toolbar);
        profile = findViewById(R.id.profileHome);
        nav_view.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layout, bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(this);
        try {
            Intent i = getIntent();
            String firstTime = i.getStringExtra("firstTime");
            if (firstTime.equals("Yes")){
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new ProfileFragment()).commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new NcstFragment()).commit();
            }
        }
        catch (Exception e){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new NcstFragment()).commit();
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainProfile fragment = new MainProfile();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



    }

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_ncst:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new NcstFragment()).commit();
                break;
            case R.id.nav_nbs:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new NbsFragment()).commit();
                break;
            case R.id.nav_mst:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new MstFragment()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new AboutUsFragment()).commit();
                break;
            case R.id.nav_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FaqFragment()).commit();
                break;
            case R.id.nav_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new ContactFragment()).commit();
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