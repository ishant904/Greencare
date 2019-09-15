package com.example.malayishant.greencare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.malayishant.greencare.Model.Rcomments;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

import static com.example.malayishant.greencare.MainActivity.mUsername;

public class AddTree extends AppCompatActivity {
    public static final String ID = "id";
    public static final String DOP = "dop";
    public static final String GEO = "geo";
    public static final String WATER = "water";
    public static final String WEED = "weed";
    public static final String MANURE = "manure";

    // Access a Cloud Firestore instance from your Activity

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button mButton;
    GeoPoint geoPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree);
        Intent intent = getIntent();
        Double latitude = intent.getDoubleExtra("latitude", 0);
        Double longitude = intent.getDoubleExtra("longitude", 0);
        geoPoint = new GeoPoint(latitude, longitude);
        mButton = findViewById(R.id.buttonAdd);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                uploadTree();
                finish();
            }
        });

    }

    public void uploadTree() {

        final Timestamp dop = Timestamp.now();

        Map<String, Object> tree = new HashMap<String, Object>();

        tree.put(DOP, Timestamp.now());
        tree.put(GEO, geoPoint);
        tree.put(WATER, Timestamp.now());
        tree.put(WEED, Timestamp.now());
        tree.put(MANURE, Timestamp.now());
        db.collection("trees")
                .add(tree)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        String s = "Sapling planted with Id :  on" + dop.toDate().toString();
                        Rcomments rc = new Rcomments(s, mUsername, null);


                        db.collection("trees")
                                .document(documentReference.getId())
                                .collection("ReportComments").add(rc);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log.w(TAG, "Error adding document", e);
                    }
                });


    }
}
