package com.panshul.traveltriangle.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.panshul.traveltriangle.Activity.PlanHoliday.Plan1;
import com.panshul.traveltriangle.Activity.PlanHoliday.Plan3;
import com.panshul.traveltriangle.R;

public class HomeFragment extends Fragment {

    View view;
    Button playMyHoliday;
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
        onclick();
        return view;
    }
    public void onclick(){
        playMyHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Plan1.class));
            }
        });
    }
}