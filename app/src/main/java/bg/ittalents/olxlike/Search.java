package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Search extends AppCompatActivity {

    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchButton = (Button)findViewById(R.id.search_button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(Search.this, AddOffer.class));
                break;
            case R.id.action_home:
                startActivity(new Intent(Search.this, Home.class));
                break;
            case R.id.action_messages:
                startActivity(new Intent(Search.this, MyMessages.class));
                break;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                startActivity(new Intent(Search.this, ViewUser.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(Search.this, Settings.class));
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
