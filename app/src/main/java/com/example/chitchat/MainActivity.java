package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chitchat.adapterss.fragchatadapter;
import com.example.chitchat.databinding.ActivityMainBinding;
import com.example.chitchat.databinding.ActivitySignupBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.viewpaager.setAdapter(new fragchatadapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewpaager);
        auth = FirebaseAuth.getInstance();
        Log.e("authuser",auth.getUid());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menue,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout:

                auth.getInstance().signOut();

                Intent intent = new Intent(MainActivity.this, signin.class);
                (MainActivity.this).finish();
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);

                startActivity(intent);


               // startActivity(new Intent(MainActivity.this,signin.class));


                break;


                case R.id.setting:
                Toast.makeText(getApplicationContext(),"setting",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,settings.class));
                    break;

                    case R.id.groupchat:
                startActivity(new Intent(MainActivity.this,groupchat.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}