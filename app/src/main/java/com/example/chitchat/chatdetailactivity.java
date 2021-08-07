package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chitchat.adapterss.ChatAdapter;
import com.example.chitchat.databinding.ActivityChatdetailactivityBinding;
import com.example.chitchat.models.messagemodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatdetailactivity extends AppCompatActivity {

    ActivityChatdetailactivityBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chatdetailactivity);

        binding = ActivityChatdetailactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // for hiding action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // senders id from firebase auth the person who is signing in
       final String senderId = auth.getUid();


        // reciever will get its data through intent via holder selection
        String recieverId =getIntent().getStringExtra("userID");
        String username = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("proficpic");

        binding.usernamechat.setText(username);
        Picasso.get().load(profilePic).placeholder(R.drawable.common_google_signin_btn_icon_light_focused).into(binding.profileImageChat);

        binding.backChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chatdetailactivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<messagemodel> messagemodels =new ArrayList<>();

        final ChatAdapter chatAdapter = new ChatAdapter(messagemodels,this);
        binding.chatrecview.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatrecview.setLayoutManager(layoutManager);


      final String senderRoom = senderId+recieverId;
      final String recieverRooom =recieverId+senderId;

      // for fetching/getting the data from data base from chat node for showing in recycler view
        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagemodels.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                // is model ke andar sara data aa gaya phir model ko messagemodel ke andar add kwa denge taki recycler view ke pass data ho show krwane ke liye
                            messagemodel model = snapshot1.getValue(messagemodel.class);
                            messagemodels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String message = binding.messgaetobesend.getText().toString();
               final messagemodel model = new messagemodel(senderId,message);
               model.setTimestampp(new Date().getTime());
               // after sending message text fied to be empty
               binding.messgaetobesend.setText("");
               // for sending message to data base
               //now data to be send to fire base  with node for senderRoom
                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats")
                                .child(recieverRooom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });

            }
        });

    }
}