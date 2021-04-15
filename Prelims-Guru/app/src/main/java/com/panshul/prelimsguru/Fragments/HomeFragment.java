package com.panshul.prelimsguru.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.panshul.prelimsguru.Activity.Bank;
import com.panshul.prelimsguru.Activity.MainActivity;
import com.panshul.prelimsguru.Activity.Railway;
import com.panshul.prelimsguru.R;

public class HomeFragment extends Fragment {

    View view;
    CardView bank,railway;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        bank = view.findViewById(R.id.bank);
        railway = view.findViewById(R.id.currentAffairs);

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Bank.class));
            }
        });
        railway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Railway.class));
            }
        });


        return view;
    }
}