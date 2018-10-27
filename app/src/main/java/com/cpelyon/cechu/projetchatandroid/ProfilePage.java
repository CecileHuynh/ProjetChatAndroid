package com.cpelyon.cechu.projetchatandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;
    private TextView email;
    private Button buttonChange;
    private TextView username;
    private String mUid;
    private String mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        email=findViewById(R.id.email);
        buttonChange=findViewById(R.id.validate_change);
        buttonChange.setOnClickListener(this);
        username=findViewById(R.id.username);
        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUid=mUser.getUid();
        DocumentReference docRef=mFirestore.document("");
        retrieveUsername();
        DisplayDataUser();
    }

    private void retrieveUsername() {
        mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot doc : snapshots) {
                    if (mUid.equals(doc.getString("uid"))) {
                        mName = doc.getString("username");
                        username.setText(mName);
                    }
                }
            }
        });
    }

    private void DisplayDataUser(){
        if(mUser!=null){
            email.setText(mAuth.getCurrentUser().getEmail());
            username.setText(mName);
            Toast.makeText(ProfilePage.this, mName, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {

        
    }
}
