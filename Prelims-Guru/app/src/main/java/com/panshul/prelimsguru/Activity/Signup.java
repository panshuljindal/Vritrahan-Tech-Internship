package com.panshul.prelimsguru.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.panshul.prelimsguru.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    EditText name,email,phone,password,plus91;
    Button signup,signUptoLogin;
    TextView signUpToLoginText;
    ImageView back;
    CardView google;
    FirebaseAuth mauth;
    DatabaseReference myref;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount acc;
    int RC_SIGN_IN = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mauth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("Users");

        findViewByIds();

        onclicklisteners();
    }
    public void findViewByIds(){
        name = findViewById(R.id.signUpName);
        email = findViewById(R.id.signUpEmail);
        plus91 = findViewById(R.id.signUp91);
        phone = findViewById(R.id.signUpMobile);
        password = findViewById(R.id.signUpPassword);
        signup = findViewById(R.id.signUpButton);
        google = findViewById(R.id.googleCardView);
        signUptoLogin = findViewById(R.id.signUptoLoginButton);
        signUpToLoginText =findViewById(R.id.signUpTextView);
        back = findViewById(R.id.imageView7);
        plus91.setFocusable(false);
    }
    public void onclicklisteners(){

        signUptoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,Login.class));
            }
        });

        signUpToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,Login.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,Login.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setEnabled(false);
                if (checkempty()){
                    if (checkMail()){
                        if (checkPhone()){
                            mauth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        signup.setEnabled(true);
                                        Toast.makeText(Signup.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mauth.getCurrentUser();
                                        String uid = user.getUid();
                                        myref.child(uid).child("name").setValue(name.getText().toString());
                                        myref.child(uid).child("email").setValue(email.getText().toString());
                                        myref.child(uid).child("phone").setValue(phone.getText().toString());
                                        myref.child(uid).child("uid").setValue(uid);
                                        myref.child(uid).child("Test").child("Bank").child("test1").setValue("NA");
                                        myref.child(uid).child("Test").child("Bank").child("test2").setValue("NA");
                                        myref.child(uid).child("Test").child("Railway").child("test1").setValue("NA");
                                        myref.child(uid).child("Test").child("Railway").child("test2").setValue("NA");
                                        startActivity(new Intent(Signup.this,Login.class));
                                    }
                                    else {
                                        signup.setEnabled(true);
                                        Toast.makeText(Signup.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                google.setEnabled(false);
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(Signup.this, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            acc = GoogleSignIn.getLastSignedInAccount(this);
            if (acc!=null){
                AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
                firebaseAuthWithGoogle(credential);

            }

        } catch (ApiException e) {
            Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(AuthCredential credential){

        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mauth.getCurrentUser();
                            String uid = user.getUid();
                            myref.child(uid).child("name").setValue(acc.getDisplayName());
                            myref.child(uid).child("email").setValue(acc.getEmail());
                            myref.child(uid).child("phone").setValue("");
                            myref.child(uid).child("uid").setValue(uid);
                            myref.child(uid).child("Test").child("Bank").child("test1").setValue("NA");
                            myref.child(uid).child("Test").child("Bank").child("test2").setValue("NA");
                            myref.child(uid).child("Test").child("Railway").child("test1").setValue("NA");
                            myref.child(uid).child("Test").child("Railway").child("test2").setValue("NA");
                            google.setEnabled(true);
                            Toast.makeText(Signup.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Signup.this,Login.class));

                        }else{
                            google.setEnabled(true);
                            task.getException().printStackTrace();
                            Toast.makeText(Signup.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
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
        Toast.makeText(Signup.this, "Please enter valid Email ID", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(Signup.this,Login.class));
    }
}