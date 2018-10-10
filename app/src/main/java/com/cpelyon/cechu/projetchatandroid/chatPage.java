package com.cpelyon.cechu.projetchatandroid;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class chatPage extends AppCompatActivity implements View.OnClickListener {

    private EditText mMessage;
    private Button mButtonValidate;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;
    private String mEMail;
    private String mUsername;
    private static final String COLLECTION_NAME="chatGroup";
    private static final String COLLECTION_NAME2="users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        mMessage=findViewById(R.id.editText);
        mButtonValidate=findViewById(R.id.button);
        mButtonValidate.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null){
            mEMail=mUser.getEmail();
        }
    }

    @Override
    public void onClick(View v) {
        Message newMessage=new Message(mMessage.getText().toString(),mEMail);
        mFirestore.collection(COLLECTION_NAME).add(newMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(chatPage.this, "Enregistrement message: Succes", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(chatPage.this, "Enregistrement message: failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
