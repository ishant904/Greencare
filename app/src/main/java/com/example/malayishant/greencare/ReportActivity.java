package com.example.malayishant.greencare;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.malayishant.greencare.Adapter.RcAdapter;
import com.example.malayishant.greencare.Model.CommunityMessage;
import com.example.malayishant.greencare.Model.Rcomments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.malayishant.greencare.MainActivity.mUsername;

public class ReportActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RcAdapter mAdapter;
    private Context context;
    private String d;
    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;
    private EditText mMessageEditText;
    private Button mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        d = bundle.getString("t");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);

        recyclerView = findViewById(R.id.rv_report);
        firestoreDB = FirebaseFirestore.getInstance();
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click

                Rcomments rc = new Rcomments(mMessageEditText.getText().toString(), mUsername, null);


                firestoreDB.collection("trees")
                        .document(d)
                        .collection("ReportComments").add(rc);
            }
        });

        loadRcList();


        firestoreListener = firestoreDB.collection("trees").document(d).collection("ReportComments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {


                            return;
                        }

                        List<Rcomments> rcList = new ArrayList<>();

                        for (DocumentSnapshot doc : documentSnapshots) {
                            Rcomments rc = doc.toObject(Rcomments.class);

                            rcList.add(rc);
                        }

                        mAdapter = new RcAdapter(rcList, getApplicationContext(), firestoreDB);
                        recyclerView.setAdapter(mAdapter);
                    }
                });

    }

    private void loadRcList() {
        firestoreDB.collection("trees").document(d).collection("ReportComments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Rcomments> rcList = new ArrayList<>();
                            // Map<String, Object> treeList = new HashMap<String, Object>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                Rcomments rc = doc.toObject(Rcomments.class);
                                //tree.setId(doc.getId());
                                rcList.add(rc);
                            }

                            mAdapter = new RcAdapter(rcList, getApplicationContext(), firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());

                        }
                    }
                });
    }
}
