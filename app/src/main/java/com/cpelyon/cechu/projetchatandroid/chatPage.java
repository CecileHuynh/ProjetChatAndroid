package com.cpelyon.cechu.projetchatandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class chatPage extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FireLog" ;
    private EditText mMessage;
    private ImageButton mButtonValidate;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;
    private String mUid;
    private String mName;
    private ListView mListview;
    private static final String COLLECTION_CHAT="chatGroup";
    private Timestamp mTimestamp;
    private List<Message> message;
    private RecyclerView recyclerView;
    private ConvAdapter convAdapter;
    private RecyclerView main_list;
    private Toolbar mTopToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);



        //   recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        mButtonValidate=findViewById(R.id.button);
        mButtonValidate.setOnClickListener(this);
        mMessage=findViewById(R.id.editText);
        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mTimestamp=Timestamp.now();
        if(mUser!=null){
            mUid=mUser.getUid();
        }


        mFirestore.collection(COLLECTION_CHAT);
        retrieveUsername();
        GetAllMessage();
        setUpRecylerView();

    }

    private void setUpRecylerView(){
        //Recycler View
        main_list = (RecyclerView) findViewById(R.id.recycler);
        main_list.setLayoutManager(new LinearLayoutManager(this));

    }

    private void GetAllMessage(){

        mFirestore.collection(COLLECTION_CHAT).orderBy("dateCreated",Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.err.println("Listen failed:" + e);
                    return;
                }
                message = new ArrayList<>();
                for (DocumentSnapshot doc : snapshots) {
                    if (doc.get("message") !=  "") {
                        Message currMessage=new Message(doc.getString("message"),doc.getString("userSender"),doc.getString("dateCreated"),doc.getString("username"));
                        message.add(currMessage);
                    }
                }
                onMessagesUpdated();
            }
        });

    }

    private void onMessagesUpdated() {
        convAdapter = new ConvAdapter(message);
        main_list.setAdapter(convAdapter);
    }


    @Override
    public void onClick(View v) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.hhmmss",Locale.FRANCE);
        User user = new User(mUid,mName,mUser.getEmail());
        Message newMessage = new Message(mMessage.getText().toString(), mUid, sdf.format(date), user.getUsername());

        mFirestore.collection(COLLECTION_CHAT).add(newMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(chatPage.this, "Enregistrement message: Succes", Toast.LENGTH_SHORT).show();
                mMessage.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(chatPage.this, "Enregistrement message: failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void retrieveUsername(){
        mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentSnapshot doc : snapshots){
                    if(mUid.equals(doc.getString("uid"))){
                        mName = doc.getString("username");
                    }
                }
            }

        });
}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}