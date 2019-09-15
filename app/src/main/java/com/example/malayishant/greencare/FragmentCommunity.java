package com.example.malayishant.greencare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.malayishant.greencare.Adapter.CommunityMsgAdapter;
import com.example.malayishant.greencare.Model.CommunityMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static com.example.malayishant.greencare.MainActivity.RC_PHOTO_PICKER;
import static com.example.malayishant.greencare.MainActivity.mUsername;


public class FragmentCommunity extends Fragment {
    // private String mUsername;


    private ListView mMessageListView;
    private CommunityMsgAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;

    public static StorageReference mChatPhotosStorageReference;
    public static DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view = inflater.inflate(R.layout.fragment_community, null);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mMessageListView = (ListView) view.findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) view.findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) view.findViewById(R.id.messageEditText);
        mSendButton = (Button) view.findViewById(R.id.sendButton);


        List<CommunityMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new CommunityMsgAdapter(getActivity(), R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);


        // Initialize Firebase components

        mChatPhotosStorageReference = FirebaseStorage.getInstance().getReference().child("chat_photos");
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("messages");
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                CommunityMessage friendlyMessage = new CommunityMessage(mMessageEditText.getText().toString(), mUsername, null);
                mMessagesDatabaseReference.push().setValue(friendlyMessage);
                // Clear input box
                mMessageEditText.setText("");
            }
        });
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                CommunityMessage friendlyMessage = dataSnapshot.getValue(CommunityMessage.class);
                mMessageAdapter.add(friendlyMessage);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);

        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                getActivity().startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        return view;
    }


}
