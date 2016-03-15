package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import model.UserSessionManager;

public class MyMessages extends CustomActivityWithMenu {

    private static Button sentMessages;
    private static Button receivedMessages;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        tabHost = (TabHost) findViewById(R.id.messages_tab_host);
        tabHost.setup();

        TabHost.TabSpec received = tabHost.newTabSpec("RECEIVED");
        received.setIndicator("RECEIVED");
        received.setContent(R.id.received);
        tabHost.addTab(received);

        //tab 2 etc...
        TabHost.TabSpec sent = tabHost.newTabSpec("SENT");
        sent.setIndicator("SENT");
        sent.setContent(R.id.sent);
        tabHost.addTab(sent);

//        sentMessages = (Button)findViewById(R.id.my_messages_sent);
//        sentMessages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        receivedMessages =  (Button) findViewById(R.id.my_messages_received);
//        receivedMessages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

}
