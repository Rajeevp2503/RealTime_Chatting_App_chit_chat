package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chitchat.databinding.ActivitySignupBinding;
import com.example.chitchat.models.user;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    ActivitySignupBinding binding;
  FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_signup);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // hide the action bar
        // for hiding of action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // getting reference of authentication
        auth = FirebaseAuth.getInstance();
        // getting reference of data basee
       database = FirebaseDatabase.getInstance();

       progressDialog= new ProgressDialog(signup.this);
       progressDialog.setTitle("creating account");
       progressDialog.setMessage("Tik ja bsdk");

        binding.signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                //now main authentication will be done
                auth.createUserWithEmailAndPassword(binding.upemail.getText().toString(), binding.uppassword.getText().toString())
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Log.e("raj", "authorization ho gaya hai");
                                    // taking uid refreence in string id
                                    String id = task.getResult().getUser().getUid();
                                    // creating object of user
                                    user u1 = new user(binding.upname.getText().toString(),binding.upemail.getText().toString(),binding.uppassword.getText().toString());
                                   // creating database and feeding obj in realtime database
                                    database.getReference().child("persons").child(id).setValue(u1);
                                    //startActivity(new Intent(signup.this,MainActivity.class));
                                    startActivity(new Intent(signup.this,MainActivity.class));
                                    Toast.makeText(getApplicationContext(), "Registerd", Toast.LENGTH_LONG).show();

                                } else {
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                    Log.e("raje", "authorization nahi hai");

                                    Toast.makeText(getApplicationContext(), "ERRRRRORR", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
        binding.gotosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this,signin.class));
            }
        });


    }
}