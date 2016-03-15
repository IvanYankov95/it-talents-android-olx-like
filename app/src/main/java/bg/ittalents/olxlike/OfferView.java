package bg.ittalents.olxlike;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import model.Offer;
import model.OfferManager;
import model.UserManager;

public class OfferView extends CustomActivityWithMenu {

    private static Offer offer;

    public static final int CALL_REQUEST_CODE = 111;
    private static TextView title;
    private static  TextView price;
    private static TextView addedFrom;
    private static  TextView address;
    private static  TextView condition;
    private static  TextView delivery;
    private static  TextView description;
    private static  ImageView mainImage;

    private static Button call;
    private static Button sendMessage;

    private OfferManager manager = OfferManager.getInstance(this);
    private UserManager userManager = UserManager.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_view);

        Bundle bundle = getIntent().getExtras();
        final long id = bundle.getLong("idOffer");
        offer = manager.getOfferByID(id);
        ArrayList<byte[]> images = offer.getImages();

        Bitmap bmp = BitmapFactory.decodeByteArray(images.get(0), 0, images.get(0).length);
        mainImage = (ImageView) findViewById(R.id.offer_view_main_image);
        mainImage.setImageBitmap(bmp);
        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferView.this, ImagesView.class);
                intent.putExtra("idOffer", id);

                startActivity(intent);
            }
        });

        title = (TextView) findViewById(R.id.offer_view_title_text);
        title.setText(offer.getName());

        price = (TextView) findViewById(R.id.offer_view_price_text);
        price.setText(String.valueOf(offer.getPrice()));

        addedFrom = (TextView) findViewById(R.id.offer_view_added_from_text);
        addedFrom.setText(offer.getSeller().getUserName());
        addedFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferView.this, ViewUser.class);
                intent.putExtra("id", offer.getSeller().getUserId());
                startActivity(intent);
            }
        });

        address = (TextView) findViewById(R.id.offer_view_address);
        address.setText(offer.getCity());

        condition = (TextView) findViewById(R.id.offer_view_condition_text);
        condition.setText(offer.getCondition());

        delivery = (TextView) findViewById(R.id.offer_view_delivery_text);

        description = (TextView) findViewById(R.id.offer_view_description_text);
        description.setText(offer.getDescription());

        sendMessage = (Button) findViewById(R.id.offer_view_send_message_button);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferView.this, SendMessage.class);
                intent.putExtra("id", offer.getSeller().getUserId());
                intent.putExtra("Username", offer.getSeller().getUserName());
                intent.putExtra("Title", offer.getName());
                startActivity(intent);
            }
        });

        call = (Button) findViewById(R.id.offer_view_phone_number_button);
        call.setText("Call " + offer.getSeller().getPhoneNumber());
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "tel:" + offer.getSeller().getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));

                int hasCallPermission = ActivityCompat.checkSelfPermission(OfferView.this, android.Manifest.permission.CALL_PHONE);
                if(hasCallPermission != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(OfferView.this, new String[]{android.Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
                }
                else
                 startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case CALL_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String url = "tel:" + offer.getSeller().getPhoneNumber();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                    int hasCallPermission = ActivityCompat.checkSelfPermission(OfferView.this, android.Manifest.permission.CALL_PHONE);
                    if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                        startActivity(intent);
                    } else {
                        call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }
                return;

        }
    }
}
