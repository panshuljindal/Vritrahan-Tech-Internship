package com.vritrahan.scholademy1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vritrahan.scholademy1.Model.UserData;
import com.vritrahan.scholademy1.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText name,email,phone,password;
    Button signup;
    TextView signUpToLoginText;
    ImageView back;
    FirebaseAuth mauth;
    DatabaseReference myref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewByIds();

        onclicklisteners();

    }
    public void findViewByIds(){
        mauth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("Users");
        name = findViewById(R.id.uname);
        email = findViewById(R.id.umail);
        phone = findViewById(R.id.uphone)   ;
        password = findViewById(R.id.upass);
        signup = findViewById(R.id.btnsignup);
        signUpToLoginText =findViewById(R.id.signUpToLogin);
    }
    public void onclicklisteners(){
        signUpToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUp.this,Login.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setEnabled(false);
                if (checkempty()){
                    if (checkMail()){
                        if (checkPhone()){
                            if (checkPassword()){
                                mauth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            signup.setEnabled(true);
                                            Toast.makeText(SignUp.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                            String uid = mauth.getCurrentUser().getUid();
                                            UserData user = new UserData(email.getText().toString(),name.getText().toString(),phone.getText().toString(),uid,"");
                                            myref.child(uid).setValue(user);
                                            myref.child(uid).child("Exam").child("IITJEE").setValue("null");
                                            myref.child(uid).child("Exam").child("UPSC").setValue("null");
                                            myref.child(uid).child("Exam").child("CLAT").setValue("null");
                                            myref.child(uid).child("Exam").child("UGC").setValue("null");
                                            myref.child(uid).child("Exam").child("CA").setValue("null");
                                            myref.child(uid).child("Exam").child("SSC").setValue("null");
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(SignUp.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                                            signup.setEnabled(true);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    public boolean checkPassword(){

        return true;
    }
    public boolean checkempty(){
        if (name.getText().toString().length()==0){
            Toast.makeText(this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.getText().toString().length()==0){
            Toast.makeText(this, "Please Enter your Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.getText().toString().length()==0){
            Toast.makeText(this, "Please Enter your Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().length()==0){
            Toast.makeText(this, "Please Enter your Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    boolean checkPhone(){
        String tempEmail = phone.getText().toString();
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
        Toast.makeText(this, "Please enter a correct Phone Number", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(SignUp.this, "Please enter valid Email ID", Toast.LENGTH_SHORT).show();
        return false;
    }

}
