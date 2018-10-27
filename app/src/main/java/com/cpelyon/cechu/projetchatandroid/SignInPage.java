package com.cpelyon.cechu.projetchatandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.INVISIBLE;


public class SignInPage extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPassword;
    private ProgressBar mProgres;
    private Button mSignInButton;
    private TextView mSignUpButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef; //mRef.child("http:s//")
    private FirebaseAuth.AuthStateListener authListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init des vues
        mEmail=(EditText)findViewById(R.id.email);
        mPassword=(EditText)findViewById(R.id.password);
        mProgres=findViewById(R.id.login_progress);
        mSignInButton =findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);
        mSignUpButton=findViewById(R.id.sign_up_link);
        mSignUpButton.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference();

        authListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent intent = new Intent(SignInPage.this, chatPage.class);
                    startActivity(intent);
                }
            }
        };

    }
    

    @Override
    public void onClick(View v) {
        int i=v.getId();
        if(i==R.id.sign_in_button) {
            mProgres.setVisibility(v.VISIBLE);
            mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignInPage.this, "Sig in : success", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(SignInPage.this, ProfilePage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignInPage.this, "Sign in: failed", Toast.LENGTH_SHORT).show();

                    }
                    mProgres.setVisibility(INVISIBLE);
                }
            });
        }
        else if(i==R.id.sign_up_link)
        {
            Intent intentSignUpPage = new Intent(SignInPage.this, SignUpPage.class);
            startActivity(intentSignUpPage);
        }
    }
}
