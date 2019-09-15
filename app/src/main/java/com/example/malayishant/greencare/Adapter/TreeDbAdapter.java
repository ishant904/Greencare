package com.example.malayishant.greencare.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.malayishant.greencare.FragmentMap;
import com.example.malayishant.greencare.Model.Tree;
import com.example.malayishant.greencare.R;
import com.example.malayishant.greencare.ReportActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class TreeDbAdapter extends RecyclerView.Adapter<TreeDbAdapter.ViewHolder> {

    private List<Tree> mtree;
    private Context context;
    private FirebaseFirestore firestoreDB;
    private String treeid;

    public TreeDbAdapter(List<Tree> tree, Context c, FirebaseFirestore firestore) {
        this.mtree = tree;
        this.context = c;
        this.firestoreDB = firestore;
    }

    @Override
    public TreeDbAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_db_item, parent, false);

        return new TreeDbAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TreeDbAdapter.ViewHolder holder, int position) {
        int itemPosition = position;
        final Tree tree = mtree.get(itemPosition);
        treeid = tree.getId();

        holder.textViewID.setText(" Id:  " + tree.getId());
        holder.textViewDop.setText("D.O.P: " + tree.getDop().toDate().toString());
        holder.textViewWater.setText("Last Watering: " + tree.getWater().toDate().toString());
        holder.textViewWeed.setText("Last Weeding: " + tree.getWeed().toDate().toString());
        holder.textViewManure.setText("Last Manure: " + tree.getManure().toDate().toString());
        holder.textViewGeopointla.setText(tree.getLatitude().toString());
        holder.textViewGeopointlo.setText(tree.getLongitude().toString());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.imageView);
                //inflating menu from xml resource
                popup.inflate(R.menu.optionmenu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.overflow_showinmap:
                                FragmentMap fragmentMap=new FragmentMap();
                                fragmentMap.setLat(tree.getLatitude());
                                fragmentMap.setLng(tree.getLongitude());
                                return true;
                            /*case R.id.menu2:
                                //handle menu2 click
                                return true;
                            case R.id.menu3:
                                //handle menu3 click
                                return true;
                            */
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

        holder.textViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext() "see u soon", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, ReportActivity.class);
                i.putExtra("t", treeid);
                context.startActivity(i);

            }
        });

        /*holder.id.setText(tree.getId());
        holder.content.setText(tree.getContent());*/

      /*  holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote(note);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(note.getId(), itemPosition);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mtree.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewID;
        TextView textViewDop;
        TextView textViewWater;
        TextView textViewWeed;
        TextView textViewManure;
        TextView textViewGeopointla;
        TextView textViewGeopointlo;
        ImageView imageView;
        TextView textViewReport;


        ViewHolder(View view) {
            super(view);
            textViewID = view.findViewById(R.id.tv_id);
            textViewDop = view.findViewById(R.id.tv_dop);
            textViewWater = view.findViewById(R.id.tv_water);
            textViewWeed = view.findViewById(R.id.tv_weed);
            textViewManure = view.findViewById(R.id.tv_manure);
            textViewGeopointla = view.findViewById(R.id.tv_geopointla);
            textViewGeopointlo = view.findViewById(R.id.tv_geopointlo);
            imageView = view.findViewById(R.id.image);
            textViewReport = view.findViewById(R.id.textView_Report);

           /* title = view.findViewById(R.id.tvTitle);
            content = view.findViewById(R.id.tvContent);

            edit = view.findViewById(R.id.ivEdit);
            delete = view.findViewById(R.id.ivDelete);*/
        }
    }
/*
    private void updateNote(Note note) {
        Intent intent = new Intent(context, NoteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("UpdateNoteId", note.getId());
        intent.putExtra("UpdateNoteTitle", note.getTitle());
        intent.putExtra("UpdateNoteContent", note.getContent());
        context.startActivity(intent);
    }

    private void deleteNote(String id, final int position) {
        firestoreDB.collection("notes")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        notesList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, notesList.size());
                        Toast.makeText(context, "Note has been deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
}