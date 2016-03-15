package model;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Ivan on 29-Jan-16.
 */
public class Message implements Comparable<Message> {

    long id;
    private String heading;
    private String text;
    private Date date;
    private long senderId;
    private long receiverId;

    public Message(long senderId, long receiverId, String heading, String text) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.heading = heading;
        this.text = text;
        this.date = new Date();
    }

    public Message(long id, long senderId, long receiverId, String heading, String text){
        this(senderId, receiverId, heading, text);
        this.id = id;

    }

    @Override
    public int compareTo(@NonNull Message another) {
        if(date.after(another.date))
            return 1;
        if(date.before(another.date))
            return -1;

        return 0;
    }

    public Date getDate() {
        return date;
    }

    public String getHeading() {
        return heading;
    }

    public String getText() {
        return text;
    }

    public long getSenderId() {
        return senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
