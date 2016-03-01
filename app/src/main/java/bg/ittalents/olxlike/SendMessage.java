package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SendMessage extends AppCompatActivity {

    Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        sendMessage = (Button)findViewById(R.id.send_message_button_send);
        sendMessage.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(SendMessage.this, AddOffer.class));
                break;
            case R.id.action_home:
                startActivity(new Intent(SendMessage.this, Home.class));
                break;
            case R.id.action_messages:
                startActivity(new Intent(SendMessage.this, MyMessages.class));
                break;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                startActivity(new Intent(SendMessage.this, ViewUser.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(SendMessage.this, Settings.class));
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
