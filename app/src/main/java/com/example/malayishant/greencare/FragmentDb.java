package com.example.malayishant.greencare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.malayishant.greencare.Adapter.TreeDbAdapter;
import com.example.malayishant.greencare.Model.Tree;
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


public class FragmentDb extends Fragment {
    FloatingActionButton fabdb;
    private RecyclerView recyclerView;
    private TreeDbAdapter mAdapter;

    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view = inflater.inflate(R.layout.fragment_db, null);
        fabdb = view.findViewById(R.id.fab_db);
        recyclerView = view.findViewById(R.id.rv_database);
        firestoreDB = FirebaseFirestore.getInstance();

        loadTreeList();

        firestoreListener = firestoreDB.collection("trees")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            // Log.e(TAG, "Listen failed!", e);

                            return;
                        }

                        List<Tree> treeList = new ArrayList<>();

                        for (DocumentSnapshot doc : documentSnapshots) {
                            Tree tree = doc.toObject(Tree.class);
                            tree.setId(doc.getId());
                            treeList.add(tree);
                        }

                        mAdapter = new TreeDbAdapter(treeList, getContext(), firestoreDB);
                        recyclerView.setAdapter(mAdapter);
                    }
                });


        fabdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AddTree.class);
                startActivity(i);
            }
        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        firestoreListener.remove();
    }

    private void loadTreeList() {
        firestoreDB.collection("trees")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Tree> treeList = new ArrayList<>();
                            // Map<String, Object> treeList = new HashMap<String, Object>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                Tree tree = doc.toObject(Tree.class);
                                tree.setId(doc.getId());
                                treeList.add(tree);
                            }

                            mAdapter = new TreeDbAdapter(treeList, getContext(), firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            // recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}

