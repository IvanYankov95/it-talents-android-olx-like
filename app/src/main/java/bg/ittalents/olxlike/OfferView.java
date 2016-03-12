package bg.ittalents.olxlike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.Offer;
import model.OfferManager;

public class OfferView extends AppCompatActivity {

    TextView title;
    TextView price;
    TextView addedFrom;
    TextView address;
    TextView condition;
    TextView delivery;
    TextView description;
    ImageView mainImage;

    OfferManager manager = OfferManager.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_view);
        Bundle bundle = getIntent().getExtras();
        long id = bundle.getLong("idOffer");
        Offer offer = manager.getOfferByID(id);
        ArrayList<byte[]> images = offer.getImages();

        Bitmap bmp = BitmapFactory.decodeByteArray(images.get(0), 0, images.get(0).length);
        mainImage = (ImageView) findViewById(R.id.offer_view_main_image);
        mainImage.setImageBitmap(bmp);

        title = (TextView) findViewById(R.id.offer_view_title_text);
        title.setText(offer.getName());

        price = (TextView) findViewById(R.id.offer_view_price_text);
        price.setText(String.valueOf(offer.getPrice()));

        addedFrom = (TextView) findViewById(R.id.offer_view_added_from_text);


        address = (TextView) findViewById(R.id.offer_view_address);
        address.setText(offer.getCity());

        condition = (TextView) findViewById(R.id.offer_view_condition_text);
        condition.setText(offer.getCondition());

        delivery = (TextView) findViewById(R.id.offer_view_delivery_text);

        description = (TextView) findViewById(R.id.offer_view_description_text);
        description.setText(offer.getDescription());


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
                startActivity(new Intent(OfferView.this, AddOffer.class));
                break;
            case R.id.action_home:
                startActivity(new Intent(OfferView.this, Home.class));
                break;
            case R.id.action_messages:
                startActivity(new Intent(OfferView.this, MyMessages.class));
                break;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                startActivity(new Intent(OfferView.this, ViewUser.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(OfferView.this, Settings.class));
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
