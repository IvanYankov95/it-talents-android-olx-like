package model;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Ivan on 29-Jan-16.
 */
public class Message implements Comparable<Message> {

    private String heading;
    private String text;
    private Date date;

    public Message(String heading, String text) {
        this.heading = heading;
        this.text = text;
        this.date = new Date();
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
}
