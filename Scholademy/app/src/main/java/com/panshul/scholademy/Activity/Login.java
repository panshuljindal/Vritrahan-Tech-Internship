package com.panshul.scholademy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.panshul.scholademy.Model.UserData;
import com.panshul.scholademy.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    Button loginButton;
    EditText email,password;
    TextView loginToSignupText;
    CardView google;
    FirebaseAuth mauth;
    DatabaseReference myref;
    String uid;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 100;
    GoogleSignInAccount acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewByIds();
        onclickListeners();

        if (mauth.getCurrentUser()!=null){
            Intent i =new Intent(Login.this,HomeScreen.class);
            i.putExtra("firstTime","No");
            startActivity(i);
        }
    }
    public void findViewByIds(){
        email = findViewById(R.id.uEmail);
        password = findViewById(R.id.upass2);
        loginButton = findViewById(R.id.btnlogin);
        loginToSignupText = findViewById(R.id.loginToSignup);
        google = findViewById(R.id.googleLoginCardView);

        mauth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("Users");

        pref = getSharedPreferences("com.panshul.scholademy.userdata",MODE_PRIVATE);
        editor = pref.edit();

    }
    public void onclickListeners(){
        loginToSignupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,SignUp.class));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                if (checkempty()){
                    if (checkMail()){
                        mauth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mauth.getCurrentUser();
                                    uid = user.getUid();
                                    saveData();
                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    loginButton.setEnabled(true);
                                    Intent i =new Intent(Login.this,HomeScreen.class);
                                    i.putExtra("firstTime","Yes");
                                    startActivity(i);
                                }
                                else {
                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    loginButton.setEnabled(true);

                                }
                            }
                        });
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
                mGoogleSignInClient = GoogleSignIn.getClient(Login.this, gso);
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
        else {
            google.setEnabled(true);
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
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
            google.setEnabled(true);
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(AuthCredential credential){

        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            google.setEnabled(true);
                            uid = mauth.getCurrentUser().getUid();
                            UserData user = new UserData(acc.getEmail(),acc.getDisplayName(),"",uid,"");
                            myref.child(uid).setValue(user);
                            myref.child(uid).child("Exam").child("IITJEE").setValue("null");
                            myref.child(uid).child("Exam").child("UPSC").setValue("null");
                            myref.child(uid).child("Exam").child("CLAT").setValue("null");
                            myref.child(uid).child("Exam").child("UGC").setValue("null");
                            myref.child(uid).child("Exam").child("CA").setValue("null");
                            myref.child(uid).child("Exam").child("SSC").setValue("null");
                            saveData();
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(Login.this,HomeScreen.class);
                            i.putExtra("firstTime","Yes");
                            startActivity(i);

                        }else{
                            google.setEnabled(true);
                            task.getException().printStackTrace();
                            Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void saveData(){
        uid = mauth.getCurrentUser().getUid();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email1 = snapshot.child(uid).child("email").getValue().toString();
                String phone = snapshot.child(uid).child("phone").getValue().toString();
                String name = snapshot.child(uid).child("name").getValue().toString();
                editor.putString("email",email1);
                editor.putString("phone",phone);
                editor.putString("name",name);
                editor.putString("uid",uid);
                editor.apply();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    boolean checkMail(){
        String tempEmail=email.getText().toString().trim();
        Pattern emailPattern=Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher emailMatcher=emailPattern.matcher(tempEmail);
        if(emailMatcher.matches())
        {
            return true;
        }
        Toast.makeText(Login.this, "Please enter valid Email ID", Toast.LENGTH_SHORT).show();
        return false;
    }
    public boolean checkempty(){

        if (email.getText().toString().length()==0){
            Toast.makeText(this, "Please Enter your Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().length()==0){
            Toast.makeText(this, "Please Enter your Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}