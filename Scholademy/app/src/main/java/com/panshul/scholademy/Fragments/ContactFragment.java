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
import com.panshul.scholademy.Model.ContactModel;
import com.panshul.scholademy.R;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactFragment extends Fragment {

    View view;

    EditText name,email,contact,message;
    Button send;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_contact, container, false);
        name = view.findViewById(R.id.contactUsNameEditText);
        email = view.findViewById(R.id.contactEmailEditText);
        contact = view.findViewById(R.id.contactPhoneEditText);
        message = view.findViewById(R.id.contactMessageEditText);
        send = view.findViewById(R.id.sendMessage);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send.setEnabled(false);
                if (checkEmpty()){
                    if (checkMail()){
                        if (checkPhone()){
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String uuid = UUID.randomUUID().toString();
                            DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Contact");
                            ContactModel model = new ContactModel(name.getText().toString(),email.getText().toString(),
                                    contact.getText().toString(),message.getText().toString(),uuid);
                            myref.child(uid).child(uuid).setValue(model);
                            email.setText("");
                            name.setText("");
                            contact.setText("");
                            message.setText("");
                            Toast.makeText(view.getContext(), "Message Sent!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        return view;
    }
    boolean checkPhone(){
        String tempEmail = contact.getText().toString();
        if(tempEmail.startsWith("6")  && tempEmail.length()==10){
            return true;
        }
        if(tempEmail.startsWith("7")  && tempEmail.length()==10){
            return true;
        }
        if(tempEmail.startsWith("8")  && tempEmail.length()==10){
            return true;
        }
        if(tempEmail.startsWith("9")  && tempEmail.length()==10){
            return true;
        }
        Toast.makeText(view.getContext(), "Please enter a correct Phone Number", Toast.LENGTH_SHORT).show();
        return false;
    }
    boolean checkMail(){
        String tempEmail=email.getText().toString().trim();
        Pattern emailPattern=Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher emailMatcher=emailPattern.matcher(tempEmail);
        if(emailMatcher.matches())
        {
            return true;
        }
        Toast.makeText(view.getContext(), "Please enter valid Email ID", Toast.LENGTH_SHORT).show();
        return false;
    }
    public boolean checkEmpty(){
        if (name.getText().length()==0){
            Toast.makeText(view.getContext(), "Please enter your Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.getText().length()==0){
            Toast.makeText(view.getContext(), "Please enter your Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (contact.getText().length()==0){
            Toast.makeText(view.getContext(), "Please enter your Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (message.getText().length()==0){
            Toast.makeText(view.getContext(), "Please enter a Message", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}