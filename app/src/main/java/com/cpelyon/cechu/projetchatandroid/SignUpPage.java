package com.cpelyon.cechu.projetchatandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.view.View.INVISIBLE;
public class SignUpPage extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPassword;
    private ProgressBar mProgress;
    private EditText mUsername;
    private TextView mSignUpButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    private String mUid;
    private FirebaseFirestore mFirestore;
    private static final String COLLECTION_NAME="users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        mEmail=(EditText)findViewById(R.id.email);
        mPassword=(EditText)findViewById(R.id.password);
        mUsername=(EditText)findViewById(R.id.username);
        mProgress=findViewById(R.id.login_progress);
        mSignUpButton =findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference();
        mFirestore=FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null){
            mUid=mUser.getUid();
        }
    }



    @Override
    public void onClick(View v) {
        mProgress.setVisibility(v.VISIBLE);

        isCheckEmail(mEmail.getText().toString(),new OnEmailCheckListener(){
            @Override
            public void onSuccess(boolean isRegistered) {
                if(isRegistered){
                   Toast.makeText(SignUpPage.this, "Email déja utilisé", Toast.LENGTH_SHORT).show();
                } else {
                    sendToFireBase();
                }
            }
        });
    }

    private void sendToFireBase()
    {
        final String sEmail=mEmail.getText().toString();
        mAuth.createUserWithEmailAndPassword(sEmail,mPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    User newUser=new User(mUid,mUsername.getText().toString(),mPassword.getText().toString(),sEmail);
                    addUserToFireStore(newUser);
                }
                else{
                    Toast.makeText(SignUpPage.this, "CreateUserWithEmail: failed", Toast.LENGTH_SHORT).show();
                }
                mProgress.setVisibility(INVISIBLE);
            }
        });
    }


    private void addUserToFireStore(User user)
    {
        mFirestore.collection(COLLECTION_NAME).document(mUid).set(user).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpPage.this, "addUsertofirestore: success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SignUpPage.this, "addUsertofirestore: failed"+mUid, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void isCheckEmail(final String email,final OnEmailCheckListener listener){
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>()
        {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task)
            {
                boolean check = !task.getResult().getSignInMethods().isEmpty();

                listener.onSuccess(check);

            }
        });

    }


}
