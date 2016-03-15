package bg.ittalents.olxlike.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bg.ittalents.olxlike.MyMessages;
import bg.ittalents.olxlike.R;
import bg.ittalents.olxlike.ViewMessage;
import model.Message;
import model.UserManager;

/**
 * Created by owner on 15/03/2016.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder>{

    private Activity activity;
    private ArrayList<Message> messages;
    UserManager userManager;

    public MessageAdapter(Activity activity, ArrayList<Message> messages) {
        this.activity = activity;
        this.messages = messages;
        userManager = UserManager.getInstance(activity);
    }

    @Override
    public MessageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(activity);
        View row = inflater.inflate(R.layout.message_row, parent, false);
        return new MessageAdapterViewHolder(row);
    }

    @Override
    public void onBindViewHolder(MessageAdapterViewHolder holder, int position) {

        Message m = messages.get(position);
        holder.title.setText(m.getHeading());
        holder.sender.setText("From: " + userManager.getUser(m.getSenderId()).getUserName());
        holder.receiver.setText("To:" + userManager.getUser(m.getReceiverId()).getUserName());
        final Intent i = new Intent(activity, ViewMessage.class);
        i.putExtra("msgId", m.getId());
        i.putExtra("sendId", m.getSenderId());
        i.putExtra("recId", m.getReceiverId());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView sender;
        TextView receiver;
        View v;

        public MessageAdapterViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.view_messages_title);
            this.sender = (TextView) itemView.findViewById(R.id.view_messages_sender);
            this.receiver = (TextView) itemView.findViewById(R.id.view_messages_receiver);
            this.v = itemView;
        }
    }
}
