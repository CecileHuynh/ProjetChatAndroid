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
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.INVISIBLE;
public class SignUpPage extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPassword;
    private ProgressBar mProgres;
    private TextView mSignUpButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        //Init des vues
        mEmail=(EditText)findViewById(R.id.email);
        mPassword=(EditText)findViewById(R.id.password);
        mProgres=findViewById(R.id.login_progress);
        mSignUpButton =findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference();
    }



    @Override
    public void onClick(View v) {
        mProgres.setVisibility(v.VISIBLE);

        isCheckEmail(mEmail.getText().toString(),new OnEmailCheckListener(){
            @Override
            public void onSuccess(boolean isRegistered) {
                if(isRegistered){
                   Toast.makeText(SignUpPage.this, "Email déja utilisé", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpPage.this, "Email valide", Toast.LENGTH_SHORT).show();
                }
            }

        });

        mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(),mPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) { Toast.makeText(SignUpPage.this, "CreateUserWithEmail: success", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();

                }
                else{
                    Toast.makeText(SignUpPage.this, "CreateUserWithEmail: failed", Toast.LENGTH_SHORT).show();

                }
                mProgres.setVisibility(INVISIBLE);
            }
        });
    }


    public void isCheckEmail(final String email,final OnEmailCheckListener listener){
        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>()
        {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task)
            {
                boolean check = !task.getResult().getProviders().isEmpty();

                listener.onSuccess(check);

            }
        });

    }


}
