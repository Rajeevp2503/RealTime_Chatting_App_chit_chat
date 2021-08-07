package com.example.chitchat.adapterss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chitchat.fragments.Callsfrag;
import com.example.chitchat.fragments.Chatsfrag;
import com.example.chitchat.fragments.Statusfrag;

public class fragchatadapter extends FragmentPagerAdapter {
    int count;
    public fragchatadapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        count = behavior;
    }

    public fragchatadapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Chatsfrag();
            case 1: return new Callsfrag();
            case 2: return  new Statusfrag();
            default:return new Callsfrag();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position==0){
            title ="CHATS";
        }
        if(position==1){
            title="STATUS";
        }
        if(position==2){
            title="CALLS";
        }
        return title;
    }
}
