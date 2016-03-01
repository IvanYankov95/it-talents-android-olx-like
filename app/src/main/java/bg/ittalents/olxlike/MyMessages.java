package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MyMessages extends AppCompatActivity {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_offer:
                startActivity(new Intent(MyMessages.this, AddOffer.class));
                break;
            case R.id.action_home:
                startActivity(new Intent(MyMessages.this, Home.class));
                break;
            case R.id.action_messages:
                startActivity(new Intent(MyMessages.this, MyMessages.class));
                break;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                startActivity(new Intent(MyMessages.this, ViewUser.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(MyMessages.this, Settings.class));
                break;
            case R.id.action_logout:
                //TODO да те логаутва...
                break;
            default:
                break;
        }

        return true;
    }
}
