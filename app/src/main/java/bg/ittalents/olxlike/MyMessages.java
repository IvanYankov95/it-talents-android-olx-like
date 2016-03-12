package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import model.UserSessionManager;

public class MyMessages extends CustomActivityWithMenu {

    Button sentMessages;
    Button receivedMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        sentMessages = (Button)findViewById(R.id.my_messages_sent);
        sentMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        receivedMessages =  (Button) findViewById(R.id.my_messages_received);
        receivedMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
