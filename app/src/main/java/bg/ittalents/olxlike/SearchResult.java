package bg.ittalents.olxlike;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import model.Offer;
import model.OfferAdapter;
import model.OfferManager;

public class SearchResult extends AppCompatActivity {

    OfferManager manager = OfferManager.getInstance(this);
    ListView listViewOffers;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        populateListViewOffers();
        listViewOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResult.this, OfferView.class);
                //TODO взимане на ид на офертата и да се пуска по интент към OfferView и там да се инициализират полетата.
                intent.putExtra("idOffer", view.getId());
                startActivity(intent);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                startActivity(new Intent(SearchResult.this, AddOffer.class));
                break;
            case R.id.action_home:
                startActivity(new Intent(SearchResult.this, Home.class));
                break;
            case R.id.action_messages:
                startActivity(new Intent(SearchResult.this, MyMessages.class));
                break;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                startActivity(new Intent(SearchResult.this, ViewUser.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(SearchResult.this, Settings.class));
                break;
            case R.id.action_logout:
                //TODO да те логаутва...
                break;
            default:
                break;
        }

        return true;
    }

    public void populateListViewOffers() {
        Bundle bundle = getIntent().getExtras();
        String word = bundle.getString("wordToSearch");
        ArrayList<Offer> offers = manager.getOffersByWord(word);
        OfferAdapter adapter = new OfferAdapter(this, offers);
        listViewOffers = (ListView) findViewById(R.id.listViewOffers);
        listViewOffers.setAdapter(adapter);
    }


}
