package com.cpelyon.cechu.projetchatandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class chatPage extends AppCompatActivity implements View.OnClickListener {

    private EditText mMessage;
    private ImageButton mButtonValidate;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;
    private String mUid;
    private ListView mListview;
    private static final String COLLECTION_CHAT="chatGroup";
    private Timestamp mTimestamp;
    private List<Message> message;
    private RecyclerView recyclerView;
    private ConvAdapter convAdapter;
    private RecyclerView main_list;
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
        GetAllMessage();
        setUpRecylerView();

    }

    private void setUpRecylerView(){
        //Recycler View
        main_list = (RecyclerView) findViewById(R.id.recycler);
        main_list.setLayoutManager(new LinearLayoutManager(this));

    }

    private void GetAllMessage(){
         mFirestore.collection(COLLECTION_CHAT).orderBy("dateCreated").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.err.println("Listen failed:" + e);
                    return;
                }
                message = new ArrayList<>();
                for (DocumentSnapshot doc : snapshots) {
                    if (doc.get("message") != null) {
                        Message currMessage=new Message(doc.getString("message"),doc.getString("userSender"),doc.getTimestamp("dateCreated"));
                        message.add(currMessage);
                    }
                }
                onMessagesUpdated();
            }
        });

    }

    //AddSnapShopListener s'execute seulement qu'a chaque changement. Donc en dehors, on aura pas accès aux données
    private void onMessagesUpdated() {
            convAdapter = new ConvAdapter(message);
            main_list.setAdapter(convAdapter);
    }


    @Override
    public void onClick(View v) {
        Message newMessage=new Message(mMessage.getText().toString(),mUid,mTimestamp);
        mFirestore.collection(COLLECTION_CHAT).add(newMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
