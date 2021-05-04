package com.vritrahan.scholademy1.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vritrahan.scholademy1.R;

public class NcstFragment extends Fragment {

    View view;
    Button button;
    String uid;
    DatabaseReference myref;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_ncst, container, false);
        button =view.findViewById(R.id.ncstRegister);
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        myref = FirebaseDatabase.getInstance().getReference("Users");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String type = snapshot.child(uid).child("Profile").child("registerNcst").getValue().toString();
                    if (type.equals("Yes")){
                        button.setText("Registered");
                        button.setEnabled(false);
                    }
                    else {
                        button.setText("Register Now");
                        button.setEnabled(true);
                    }

                }
                catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                myref.child(uid).child("Profile").child("registerNcst").setValue("Yes");
                button.setText("Registered");
                button.setEnabled(false);
                Toast.makeText(v.getContext(), "Registered", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}