package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import model.UserSessionManager;

public class Home extends AppCompatActivity {

    private static Button searchButton;
    private TextView helloMsg;
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
        searchButton = (Button) findViewById(R.id.home_button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        MenuManager manager = new MenuManager();

        Class goingToClass = manager.onItemClick(item, session);

        if(goingToClass == null)
            session.logoutUser();
        else
            startActivity(new Intent(this, goingToClass));

        return true;
    }

}
