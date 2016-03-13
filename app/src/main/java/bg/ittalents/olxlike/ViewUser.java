package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.Offer;
import model.OfferAdapter;
import model.OfferManager;
import model.UserAcc;
import model.UserManager;
import model.UserSessionManager;

public class ViewUser extends CustomActivityWithMenu {

    TextView username;
    TextView fname;
    TextView lname;
    TextView city;
    TextView phone;
    ListView offersList;

    Bundle bundle;
    UserManager userManager;
    OfferManager offerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        userManager = UserManager.getInstance(this);
        offerManager = OfferManager.getInstance(this);

        username = (TextView) findViewById(R.id.usernameField);
        fname = (TextView) findViewById(R.id.firstName);
        lname = (TextView) findViewById(R.id.lastName);
        city = (TextView) findViewById(R.id.city);
        phone = (TextView) findViewById(R.id.phone);
        offersList = (ListView) findViewById(R.id.user_offers_list);

        bundle = getIntent().getExtras();
        long id = bundle.getLong("id");
        UserAcc user = userManager.getUser(id);
        ArrayList<Offer> userOffers = offerManager.getOffersByUser(id);

        username.setText(user.getUserName());
        fname.setText(user.getFirstName());
        lname.setText(user.getLastName());
        city.setText(user.getCity());
        phone.setText(user.getPhoneNumber());

        OfferAdapter adapter = new OfferAdapter(this, userOffers);
        offersList.setAdapter(adapter);
        offersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewUser.this, OfferView.class);
                intent.putExtra("idOffer", id);
                startActivity(intent);
            }
        });

    }

}
