package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import model.UserSessionManager;

public class Home extends AppCompatActivity {

    private static Button searchButton;
    private TextView helloMsg;
    private EditText textToSearch;
    private UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        session = new UserSessionManager(this);

        helloMsg = (TextView) findViewById(R.id.hello);
        if(session.isUserLoggedIn()){
            // get user details
            HashMap<String, String> user = session.getUserDetails();

            helloMsg.setText("Hello, " + user.get(session.KEY_NAME));
        }
        else{
            helloMsg.setText("Hello, guest");
        }
        textToSearch = (EditText) findViewById(R.id.home_text_to_search);
        searchButton = (Button) findViewById(R.id.home_button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = textToSearch.getText().toString();
                Intent intent = new Intent(Home.this, SearchResult.class);
                intent.putExtra("wordToSearch", word);
                startActivity(intent);
            }
        });
    }

    public void goToLogIn(View view) {
        startActivity(new Intent(Home.this, LogIn.class));
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
                if(session.isUserLoggedIn()) {
                    startActivity(new Intent(Home.this, AddOffer.class));
                }
                else{
                    startActivity(new Intent(Home.this, LogIn.class));
                }
                break;
            case R.id.action_home:
                startActivity(new Intent(Home.this, Home.class));
                break;
            case R.id.action_messages:
                startActivity(new Intent(Home.this, MyMessages.class));
                break;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                startActivity(new Intent(Home.this, ViewUser.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(Home.this, Settings.class));
                break;
            case R.id.action_logout:
                session.logoutUser();
                startActivity(new Intent(Home.this, Home.class));
                break;
            default:
                break;
        }

        return true;
    }

}
