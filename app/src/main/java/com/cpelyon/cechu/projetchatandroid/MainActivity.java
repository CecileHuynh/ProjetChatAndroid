package com.cpelyon.cechu.projetchatandroid;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.INVISIBLE;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPassword;
    private ProgressBar mProgres;
    private Button mButton;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef; //mRef.child("http:s//")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init des vues
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mProgres=findViewById(R.id.login_progress);
        mButton=findViewById(R.id.sign_in_button);
        mButton.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference();

    }

    @Override
    public void onClick(View v) {
        mProgres.setVisibility(v.VISIBLE);
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Connexion impossible, veuillez réessayer", Toast.LENGTH_SHORT).show();

                }
                mProgres.setVisibility(INVISIBLE);
            }
        });
    }
}
