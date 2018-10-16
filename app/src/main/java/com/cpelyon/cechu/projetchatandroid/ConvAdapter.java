package com.cpelyon.cechu.projetchatandroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ConvAdapter extends RecyclerView.Adapter<ConvAdapter.MyViewHolder> {

    public List<Message> list_messages;

    public void setList_messages(List<Message> list_messages) {
        this.list_messages = list_messages;
    }

    public ConvAdapter(List<Message> list_messages) {
        this.list_messages = list_messages;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(rootView);
        vh.authorView.setText(list_messages.get(i).getUserSender());
        vh.contentView.setText(list_messages.get(i).getMessage());
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder vh, int i) {
        vh.authorView.setText(list_messages.get(i).getUserSender());
        vh.contentView.setText(list_messages.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return list_messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView authorView;
        private TextView contentView;

        public MyViewHolder(View rootView) {
            super(rootView);
            authorView = (TextView) itemView.findViewById(R.id.author);
            contentView = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
