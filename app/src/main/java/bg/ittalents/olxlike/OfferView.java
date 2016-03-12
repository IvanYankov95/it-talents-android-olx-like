package bg.ittalents.olxlike;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import model.Offer;
import model.OfferManager;

public class OfferView extends CustomActivityWithMenu {

    private static TextView title;
    private static  TextView price;
    private static TextView addedFrom;
    private static  TextView address;
    private static  TextView condition;
    private static  TextView delivery;
    private static  TextView description;
    private static  ImageView mainImage;

    private OfferManager manager = OfferManager.getInstance(this);

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

}
