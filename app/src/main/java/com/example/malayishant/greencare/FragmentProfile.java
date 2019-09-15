package com.example.malayishant.greencare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class FragmentProfile extends Fragment {
    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view = inflater.inflate(R.layout.fragment_profile, null);
        mButton = view.findViewById(R.id.button_signout);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                signOut();
            }
        });
        return view;
    }

    public void signOut() {
        //AuthUI.getInstance().signOut(getActivity());
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "see u soon", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
