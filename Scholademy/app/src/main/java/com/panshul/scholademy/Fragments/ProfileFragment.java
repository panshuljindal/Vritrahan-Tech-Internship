package com.panshul.scholademy.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.panshul.scholademy.Model.Profile;
import com.panshul.scholademy.Model.UserData;
import com.panshul.scholademy.R;

public class ProfileFragment extends Fragment {

    View view;
    EditText name,emailID,phoneNumber,age,nameOfCoaching,coursName,fees;
    Spinner states;
    String local,type;
    Button submit;
    CheckBox iitjee,neet,upsecse,ies,gate,ugcnet,clat,statepcs,judiciary,ca,ssccgl,cs,bankpo,rbi,epfo,nabard,online,offline;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        findViewByIds();
        loadData();
        spinner();
        local="null";
        type="null";
        onclick();
        return view;
    }
    public void loadData(){
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.scholademy.userdata", Context.MODE_PRIVATE);
        name.setText(pref.getString("name",""));
        emailID.setText(pref.getString("email",""));
        phoneNumber.setText(pref.getString("phone",""));

    }
    public void spinner(){
        String[] items = new String[]{"Delhi", "Mumbai", "Kolkata"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        states.setAdapter(adapter);
    }
    public void onclick(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (local.length()==0){
                   local="null";
                }
                if (checkEmpty()){
                    if (local.equals("null")==false){
                        if (type.equals("null")==false){
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            if (states.getSelectedItem().toString().isEmpty()==false){
                                Profile  profile = new Profile(name.getText().toString(),emailID.getText().toString(),phoneNumber.getText().toString(),
                                        age.getText().toString(),uid,local,type,nameOfCoaching.getText().toString(),coursName.getText().toString(),
                                        fees.getText().toString(),states.getSelectedItem().toString(),"No");
                                DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Users");
                                myref.child(uid).child("Profile").setValue(profile);
                                myref.child(uid).child("age").setValue(age.getText().toString());
                                myref.child(uid).child("phone").setValue(phoneNumber.getText().toString());
                                Toast.makeText(view.getContext(), "Profile Successfully Updated", Toast.LENGTH_SHORT).show();
                                NcstFragment fragment = new NcstFragment();
                                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.frameLayout, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }
                        else {
                            Toast.makeText(view.getContext(), "Please enter your online/offline preference", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(view.getContext(), "Please enter Exam Type", Toast.LENGTH_SHORT).show();
                    }
                }
           }
        });
    }
    public boolean checkEmpty(){
        if (name.getText().length()==0){
            name.requestFocus();
            name.setError("Please enter your Name");
            return false;
        } if (emailID.getText().length()==0){
            emailID.requestFocus();
            emailID.setError("Please enter Email ID");
            return false;
        }
        if (phoneNumber.getText().length()==0){
            phoneNumber.requestFocus();
            phoneNumber.setError("Please enter phone number");
            return false;
        }
        if (age.getText().length()==0){
            age.requestFocus();
            age.setError("Please enter your Age");
            return false;
        }
        return true;
    }
    public void findViewByIds(){
        name = view.findViewById(R.id.profileName);
        emailID= view.findViewById(R.id.profileEmail);
        phoneNumber= view.findViewById(R.id.profilePhone);
        age = view.findViewById(R.id.profileAge);
        nameOfCoaching= view.findViewById(R.id.profileNameCoaching);
        coursName = view.findViewById(R.id.profileCourseName);
        fees= view.findViewById(R.id.profileApproxFees);
        states= view.findViewById(R.id.spinner1);
        iitjee= view.findViewById(R.id.checkBoxIitJee);
        neet= view.findViewById(R.id.checkBoxNeet);
        upsecse= view.findViewById(R.id.checkBoxUpscCse);
        ies= view.findViewById(R.id.checkBoxIes);
        gate= view.findViewById(R.id.checkBoxGate);
        ugcnet= view.findViewById(R.id.checkBoxUgcNet);
        clat= view.findViewById(R.id.checkBoxClat);
        statepcs= view.findViewById(R.id.checkBoxStatePcs);
        judiciary= view.findViewById(R.id.checkBoxJudiciary);
        ca= view.findViewById(R.id.checkBoxCa);
        ssccgl= view.findViewById(R.id.checkBoxSscCgl);
        cs= view.findViewById(R.id.checkBoxCs);
        bankpo= view.findViewById(R.id.checkBoxBankPo);
        rbi= view.findViewById(R.id.checkBoxRbi);
        epfo= view.findViewById(R.id.checkBoxEpfo);
        nabard= view.findViewById(R.id.checkBoxNabard);
        online= view.findViewById(R.id.checkBoxOnline);
        offline= view.findViewById(R.id.checkBoxOffline);
        submit= view.findViewById(R.id.profileSubmit);

        iitjee.setOnClickListener(this::OnCheckBoxClicked);
        neet.setOnClickListener(this::OnCheckBoxClicked);
        upsecse.setOnClickListener(this::OnCheckBoxClicked);
        ies.setOnClickListener(this::OnCheckBoxClicked);
        gate.setOnClickListener(this::OnCheckBoxClicked);
        ugcnet.setOnClickListener(this::OnCheckBoxClicked);
        clat.setOnClickListener(this::OnCheckBoxClicked);
        statepcs.setOnClickListener(this::OnCheckBoxClicked);
        judiciary.setOnClickListener(this::OnCheckBoxClicked);
        ca.setOnClickListener(this::OnCheckBoxClicked);
        ssccgl.setOnClickListener(this::OnCheckBoxClicked);
        cs.setOnClickListener(this::OnCheckBoxClicked);
        bankpo.setOnClickListener(this::OnCheckBoxClicked);
        rbi.setOnClickListener(this::OnCheckBoxClicked);
        epfo.setOnClickListener(this::OnCheckBoxClicked);
        nabard.setOnClickListener(this::OnCheckBoxClicked);

        online.setOnClickListener(this::OnCheckBoxClickedCourse);
        offline.setOnClickListener(this::OnCheckBoxClickedCourse);

    }
    public void OnCheckBoxClickedCourse(View view){
        boolean checked=((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.checkBoxOnline:
                if (online.isChecked()){
                    type="Online";
                    offline.setChecked(false);
                }
                else {
                    type="Offline";
                    offline.setChecked(true);
                }

            case  R.id.checkBoxOffline:
                if (offline.isChecked()){
                    type="Offline";
                    online.setChecked(false);
                }
                else {
                    type="Online";
                    online.setChecked(true);
                }
        }
        //Log.i("Line",type);
    }
    public void OnCheckBoxClicked(View view){
        local="";
        if (iitjee.isChecked()){
            local = local+"/IIT JEE";
        }
        if (neet.isChecked()){
            local = local+"/NEET";
        }
        if (upsecse.isChecked()){
            local = local+"/UPSC CSE";
        }
        if (ies.isChecked()){
            local = local+"/IES";
        }
        if (gate.isChecked()){
            local = local+"/GATE";
        }
        if (ugcnet.isChecked()){
            local = local+"/UGC NET";
        }
        if (clat.isChecked()){
            local = local+"/CLAT";
        }
        if (statepcs.isChecked()){
            local = local+"/STATE PCS";
        }
        if (judiciary.isChecked()){
            local = local+"/Judiciary";
        }
        if (ca.isChecked()){
            local = local+"/CA";
        }
        if (ssccgl.isChecked()){
            local = local+"/SSC CGL";
        }
        if (cs.isChecked()){
            local = local+"/CS";
        }
        if (bankpo.isChecked()){
            local = local+"/BANK PO";
        }
        if (rbi.isChecked()){
            local = local+"/RBI";
        }
        if (epfo.isChecked()){
            local = local+"/EPFO";
        }
        if (nabard.isChecked()){
            local = local+"/NABARD";
        }
    }
}