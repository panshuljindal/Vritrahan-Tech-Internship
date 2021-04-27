package com.panshul.traveltriangle.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.experimental.Experimental;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.panshul.traveltriangle.Activity.Signup;
import com.panshul.traveltriangle.Model.UserData;
import com.panshul.traveltriangle.R;

public class LoginFragment extends Fragment {

    View view;
    TextView loginTosignup;
    EditText email,password;
    CardView google;
    Button login;
    FirebaseAuth mauth;
    DatabaseReference myref;
    GoogleSignInAccount acc;
    int RC_SIGN_IN = 100;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_login, container, false);

        findViewByIds();
        onclick();
        return view;
    }
    public void findViewByIds(){
        mauth = FirebaseAuth.getInstance();
        myref = FirebaseDatabase.getInstance().getReference("Users");
        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);
        google = view.findViewById(R.id.googleCardViewLogin);
        login = view.findViewById(R.id.loginButton);
        loginTosignup = view.findViewById(R.id.loginToSignup);
    }
    public void onclick(){
        loginTosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Signup.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                mauth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            login.setEnabled(true);
                            Toast.makeText(view.getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            saveData();

                        }
                        else {
                            Toast.makeText(view.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                            email.setText("");
                            password.setText("");
                        }
                    }
                });
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                google.setEnabled(false);
                googleSignup();
            }
        });

    }
    public void googleSignup(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(view.getContext(), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleSignin(task);
        }
    }
    public void googleSignin(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            acc = GoogleSignIn.getLastSignedInAccount(view.getContext());
            if (acc!=null){
                AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
                firebaseAuthWithGoogle(credential);
            }
        } catch (ApiException e) {
            Toast.makeText(view.getContext(), "SignUp Failed", Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(AuthCredential credential){

        mauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String uid = mauth.getCurrentUser().getUid();
                    myref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                UserData userData = snapshot.child(uid).getValue(UserData.class);
                                saveData();
                                Toast.makeText(view.getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                BookingFragment momDiagFrag = new BookingFragment();
                                FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout,momDiagFrag);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                            catch (Exception e){
                                Toast.makeText(view.getContext(), "SignUp First", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    google.setEnabled(true);
                    Toast.makeText(view.getContext(), "SignUp Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void saveData(){
        String uid = mauth.getCurrentUser().getUid();
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    UserData user = snapshot.child(uid).getValue(UserData.class);
                    SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.travel.userdata", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name",user.getName());
                    editor.putString("uid",user.getUid());
                    editor.putString("email",user.getEmail());
                    editor.putString("phone",user.getPhone());
                    editor.apply();
                }
                catch (NullPointerException e){
                    Toast.makeText(view.getContext(), "Please SignUp Before Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}