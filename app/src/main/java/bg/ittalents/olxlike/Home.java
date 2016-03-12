package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import model.OfferManager;
import model.UserSessionManager;

public class Home extends CustomActivityWithMenu {

    private static Button searchButton;
    private static TextView helloMsg;
    private static EditText textToSearch;
    GridView categoryList;
    ArrayList<String> categories;
    OfferManager offerManager;

    //test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                if(word.equals("")) {
                    textToSearch.setError("Please input key word to search.");
                }
                else {
                    Intent intent = new Intent(Home.this, SearchResult.class);
                    intent.putExtra("wordToSearch", word);
                    startActivity(intent);
                }
            }
        });

        offerManager = OfferManager.getInstance(this);
        categoryList = (GridView) findViewById(R.id.categoryList);
        categories = offerManager.getAllCategories();
        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        categoryList.setAdapter(adapter);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Home.this, Category.class);
                TextView name = (TextView) view.findViewById(R.id.catName);
                intent.putExtra("category", name.getText().toString());
                startActivity(intent);
            }
        });

    }

    public void goToLogIn(View view) {
        startActivity(new Intent(Home.this, LogIn.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(session.isUserLoggedIn()){
            // get user details
            HashMap<String, String> user = session.getUserDetails();

            helloMsg.setText("Hello, " + user.get(session.KEY_NAME));
        }
        else{
            helloMsg.setText("Hello, guest");
        }
    }

}
