package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chitchat.adapterss.ChatAdapter;
import com.example.chitchat.databinding.ActivityChatdetailactivityBinding;
import com.example.chitchat.databinding.ActivityGroupchatBinding;
import com.example.chitchat.models.messagemodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class groupchat extends AppCompatActivity {

    ActivityGroupchatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupchatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.backChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(groupchat.this,MainActivity.class));

            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final ArrayList<messagemodel> messagemodels = new ArrayList<>();

        // sender id to be take from get instance
        final  String senderID= FirebaseAuth.getInstance().getUid();

        binding.usernamechat.setText("Friends Group");

        final ChatAdapter  adapter = new ChatAdapter(messagemodels,this);
        binding.chatrecview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatrecview.setLayoutManager(layoutManager);

        // for fetching data from data base
        database.getReference().child("Group chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagemodels.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            // is model ke andar sara data aa gaya phir model ko messagemodel ke andar add kwa denge taki recycler view ke pass data ho show krwane ke liye
                            messagemodel model = snapshot1.getValue(messagemodel.class);
                            messagemodels.add(model);
                        }
                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String message = binding.messgaetobesend.getText().toString();
               // jaise hi sendmsg pr click hoga message model ka object  bana denge aur phir databas me bhej denge
                final  messagemodel model = new messagemodel(senderID, message);
                model.setTimestampp(new Date().getTime());

                binding.messgaetobesend.setText("");
                // ab data base me data bhej denge aur node me set krwa denge
                database.getReference().child("Group chat")
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

            }
        });

    }
}