package bg.ittalents.olxlike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.Offer;
import bg.ittalents.olxlike.adapters.OfferAdapter;
import model.OfferManager;

public class Category extends CustomActivityWithMenu {

    Bundle bundle;
    ArrayList<Offer> offers;
    OfferManager manager;
    String category;
    TextView text;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        manager = OfferManager.getInstance(this);
        list = (ListView) findViewById(R.id.categoryOffers);
        bundle = getIntent().getExtras();
        category = bundle.getString("category");
        text = (TextView) findViewById(R.id.category);
        text.setText(category);
        offers = manager.getOffersByCategory(category);
        if(!offers.isEmpty()) {
            OfferAdapter adapter = new OfferAdapter(this, offers);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Category.this, OfferView.class);
                    intent.putExtra("idOffer", id);
                    startActivity(intent);
                }
            });
        }
    }

}
