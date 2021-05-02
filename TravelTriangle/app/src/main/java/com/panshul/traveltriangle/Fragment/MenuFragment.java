package com.panshul.traveltriangle.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.panshul.traveltriangle.R;

public class MenuFragment extends Fragment {

    View view;
    TextView name,email,initials;
    SharedPreferences pref;
    ConstraintLayout logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_menu, container, false);

        name = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        initials = view.findViewById(R.id.profileInitials);
        logout = view.findViewById(R.id.logoutCl);

        pref= view.getContext().getSharedPreferences("com.panshul.travel.userdata", Context.MODE_PRIVATE);
        name.setText(pref.getString("name","Name"));
        email.setText(pref.getString("email","Email ID"));
        initials.setText("-");
        String name1=pref.getString("name","");
        try {
            char name2 = Character.toUpperCase(name1.charAt(0));
            initials.setText(String.valueOf(name2));
        }
        catch (StringIndexOutOfBoundsException e){

        }



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Toast.makeText(v.getContext(), "Logged Out", Toast.LENGTH_SHORT).show();

                    FirebaseAuth.getInstance().signOut();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.apply();
                    name.setText("");
                    email.setText("");
                    initials.setText("");
                    LoginFragment momDiagFrag = new LoginFragment();
                    FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,momDiagFrag);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else {
                    Toast.makeText(v.getContext(), "Login First!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
}