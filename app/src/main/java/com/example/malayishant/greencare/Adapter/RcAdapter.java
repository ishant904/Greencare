package com.example.malayishant.greencare.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.malayishant.greencare.Model.Rcomments;
import com.example.malayishant.greencare.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RcAdapter extends RecyclerView.Adapter<RcAdapter.ViewHolder> {

    private List<Rcomments> rcomments;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public RcAdapter(List<Rcomments> rc, Context c, FirebaseFirestore firestore) {
        this.rcomments = rc;
        this.context = c;
        this.firestoreDB = firestore;
    }

    @Override
    public RcAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc, parent, false);

        return new RcAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int itemPosition = position;
        Rcomments rcomment = rcomments.get(itemPosition);
        //holder.textViewRC.setText(rcomment.getText());

        boolean isPhoto = rcomment.getPhotoUrl() != null;
        if (isPhoto) {
            viewHolder.textViewRc.setVisibility(View.GONE);
         
            viewHolder.imageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.imageView.getContext())
                    .load(rcomment.getPhotoUrl())
                    .into(viewHolder.imageView);
        } else {
            viewHolder.textViewRc.setVisibility(View.VISIBLE);
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.textViewRc.setText(rcomment.getText());
        }
        viewHolder.textViewUser.setText(rcomment.getName());


    }

    @Override
    public int getItemCount() {
        return rcomments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRc;
        ImageView imageView;
        TextView textViewUser;


        ViewHolder(View view) {
            super(view);
            textViewRc = view.findViewById(R.id.rcmessageTextView);
            imageView = view.findViewById(R.id.rcphotoImageView);
            textViewUser = view.findViewById(R.id.rcnameTextView);

        }
    }
}
