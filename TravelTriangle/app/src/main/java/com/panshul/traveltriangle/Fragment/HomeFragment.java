package com.panshul.traveltriangle.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.panshul.traveltriangle.Activity.PlanHoliday.Plan1;
import com.panshul.traveltriangle.Activity.PlanHoliday.Plan3;
import com.panshul.traveltriangle.Activity.PlanHoliday.Plan4;
import com.panshul.traveltriangle.Activity.PlanHoliday.Plan5;
import com.panshul.traveltriangle.Activity.PlanHoliday.Plan6;
import com.panshul.traveltriangle.Activity.PlanHoliday.Plan7;
import com.panshul.traveltriangle.Activity.SearchActivity;
import com.panshul.traveltriangle.R;

public class HomeFragment extends Fragment {

    View view;
    Button playMyHoliday;
    ConstraintLayout searchCl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);

        playMyHoliday = view.findViewById(R.id.planMyHolidayButton);
        searchCl = view.findViewById(R.id.searchBg);
        searchCl.bringToFront();
        onclick();
        return view;
    }
    public void onclick(){
        searchCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), SearchActivity.class));
            }
        });
        playMyHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                    startActivity(new Intent(view.getContext(), Plan1.class));
                }
                else {
                    Toast.makeText(view.getContext(), "Please Login before Planning", Toast.LENGTH_SHORT).show();
                    LoginFragment momDiagFrag = new LoginFragment();
                    FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,momDiagFrag);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }
}