package com.panshul.scholademy.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.panshul.scholademy.Model.NbsModel;
import com.panshul.scholademy.R;

public class NbsFragment extends Fragment {

    View view;
    EditText fatherName,fatherProfession,fatherAnnualIncome,fatherIncomeProof;
    Button submit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_nbs, container, false);
        fatherName = view.findViewById(R.id.nbsFatherNameEditText);
        fatherProfession = view.findViewById(R.id.nbsFatherProfEditText);
        fatherAnnualIncome = view.findViewById(R.id.nbsFatherIncomeEditText);
        fatherIncomeProof = view.findViewById(R.id.nbsIncomeProof);
        submit = view.findViewById(R.id.submitNbs);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setEnabled(false);
                if (checkEmpty()){
                    NbsModel model = new NbsModel(
                            fatherName.getText().toString(),fatherProfession.getText().toString(),
                            fatherAnnualIncome.getText().toString(),fatherIncomeProof.getText().toString()
                    );
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Users");
                    myref.child(uid).child("NBS").setValue(model);
                    fatherName.setText("");
                    fatherProfession.setText("");
                    fatherAnnualIncome.setText("");
                    fatherIncomeProof.setText("");
                    Toast.makeText(v.getContext(), "Successfully submitted!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    public boolean checkEmpty(){
        if (fatherName.getText().length()==0){
            Toast.makeText(view.getContext(), "Please enter your Father's Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (fatherProfession.getText().length()==0){
            Toast.makeText(view.getContext(), "Please enter your Father's Profession", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (fatherAnnualIncome.getText().length()==0){
            Toast.makeText(view.getContext(), "Please enter your Father's Annual Income", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (fatherIncomeProof.getText().length()==0){
            Toast.makeText(view.getContext(), "Please enter Annual Income's proof", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}