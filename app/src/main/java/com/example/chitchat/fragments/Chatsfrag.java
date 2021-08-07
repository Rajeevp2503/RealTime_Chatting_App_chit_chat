package com.example.chitchat.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.chitchat.R;
import com.example.chitchat.adapterss.UsersRecycler;
import com.example.chitchat.databinding.FragmentChatsfragBinding;
import com.example.chitchat.models.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Chatsfrag extends Fragment {

    FragmentChatsfragBinding binding;
    ArrayList<user> list= new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        database =FirebaseDatabase.getInstance();

        binding = FragmentChatsfragBinding.inflate(inflater,container,false);

        UsersRecycler adapter = new UsersRecycler(list,getContext());
        binding.chatrecyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatrecyclerview.setLayoutManager(layoutManager);

        database.getReference().child("persons").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    user userrr = dataSnapshot.getValue(user.class);
                    userrr.setUserid(dataSnapshot.getKey());
                    //notshowing the user who is login in the appp
                    if(!userrr.getUserid().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(userrr);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();

    }
}