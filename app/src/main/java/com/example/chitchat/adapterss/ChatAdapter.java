package com.example.chitchat.adapterss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitchat.R;
import com.example.chitchat.models.messagemodel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter  {

    ArrayList<messagemodel> messagemodels;
    Context context;
    int SENDER_VIEW_TYPE =1;
    int RECIEVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<messagemodel> messagemodels, Context context) {
        this.messagemodels = messagemodels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return  new SenderViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return  new RecieverViewHolder(view);
        }

    }

    // for the type of view for sender or reciever
    @Override
    public int getItemViewType(int position) {
        if(messagemodels.get(position).getuID().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECIEVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // is message model ke pass position position ke sath  ka object hai
        messagemodel messagemodel = messagemodels.get(position);
        if(holder.getClass()== SenderViewHolder.class){
            ((SenderViewHolder)holder).sendermsg.setText(messagemodel.getMessage());
        }
        else {
            ((RecieverViewHolder)holder).recievermsg.setText(messagemodel.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messagemodels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView recievermsg, recievertime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recievermsg = itemView.findViewById(R.id.recievermessage);
            recievertime=itemView.findViewById(R.id.recievertime);

        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView sendermsg , sendertime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermsg = itemView.findViewById(R.id.sendermessage);
            sendertime=  itemView.findViewById(R.id.Sendertime);
        }
    }
}
