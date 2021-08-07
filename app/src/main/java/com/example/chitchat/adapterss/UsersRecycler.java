package com.example.chitchat.adapterss;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitchat.R;
import com.example.chitchat.chatdetailactivity;
import com.example.chitchat.models.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersRecycler extends  RecyclerView.Adapter<UsersRecycler.viewholder>{

    ArrayList<user> list;
    Context context;

    public UsersRecycler(ArrayList<user> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.samplechat_show,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        user useer = list.get(position);

        Picasso.get().load(useer.getPurl()).placeholder(R.drawable.ic_launcher_foreground).into(holder.image);
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.image);
        holder.username.setText(useer.getUsername());

        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid()+useer.getUserid())
                .orderByChild("timestampp")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if(snapshot.hasChildren()){
                          for(DataSnapshot snapshot1 :snapshot.getChildren()){
                              holder.lastmessage.setText(snapshot1.child("message").getValue().toString());
                          }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, chatdetailactivity.class);
                intent.putExtra("userID", useer.getUserid());
                intent.putExtra("proficpic", useer.getPurl());
                intent.putExtra("userName", useer.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class  viewholder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView username , lastmessage;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image_chat);
            username=itemView.findViewById(R.id.usernamee);
            lastmessage =itemView.findViewById(R.id.lastmessage);
        }
    }
}
