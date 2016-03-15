package bg.ittalents.olxlike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import model.Offer;
import model.OfferManager;

public class EditOffer extends AddOffer {

    private OfferManager manager = OfferManager.getInstance(this);

    private static CheckBox archiveBox;

    private static boolean isArchived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_offer);

        archiveBox = (CheckBox) findViewById(R.id.edit_offer_archive_checkbox);

        Bundle bundle = getIntent().getExtras();
        final long id = bundle.getLong("idOffer");
        final Offer offer = manager.getOfferByID(id);
        ArrayList<byte[]> images = offer.getImages();

        for (int i = 0; i < images.size(); i++){
            Bitmap bmp = BitmapFactory.decodeByteArray(images.get(i), 0, images.get(0).length);
            switch (i){
                case 0:
                    mainPicture.setImageBitmap(bmp);
                    break;
                case 1:
                    picture1.setImageBitmap(bmp);
                    break;
                case 2:
                    picture2.setImageBitmap(bmp);
                    break;
                case 3:
                    picture3.setImageBitmap(bmp);
                    break;
                case 4:
                    picture4.setImageBitmap(bmp);
                    break;
                case 5:
                    picture5.setImageBitmap(bmp);
                    break;
                case 6:
                    picture6.setImageBitmap(bmp);
                    break;
                default:
                    Toast.makeText(EditOffer.this, "Something went wrong in edit Offer on create switch", Toast.LENGTH_SHORT).show();
            }
        }

        title.setText(offer.getName());

        categorySpinner.setSelection(((ArrayAdapter) categorySpinner.getAdapter()).getPosition(offer.getCategory()));

        price.setText(String.valueOf(offer.getPrice()));

        String cn = offer.getCondition();
        if(cn.equalsIgnoreCase("new"))
            condition.check(R.id.radioButton);
        else
            condition.check(R.id.radioButton2);

        description.setText(offer.getDescription());

        city.setText(offer.getCity());

        addOfferButton.setText("Save edit");
        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRequirements()){
                    Date date = new Date();

                    RadioGroup rg = (RadioGroup)findViewById(R.id.add_offer_condition_radio);
                    String conditionString = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                    long userId = Long.parseLong(user.get(session.KEY_ID));
                    if(archiveBox.isChecked())
                        isArchived = true;
                    Offer of = new Offer(null, title.getText().toString(), description.getText().toString(),Double.parseDouble(price.getText().toString()), conditionString, selectedCategory, city.getText().toString(), isArchived, pictures, date);
                    long offerID = offerManager.updateOffer(offer.getId(), of);

                    if (offerID != -1) {
                        Toast.makeText(EditOffer.this, "Offer added successfully", Toast.LENGTH_SHORT).show();
                        notifyUserForNewOffer(userId, offerID);
                    }
                    Intent offerViewIntent = new Intent(EditOffer.this, OfferView.class);
                    offerViewIntent.putExtra("idOffer", offerID);
                    startActivity(offerViewIntent);
                }
            }
        });
    }

    @Override
    protected boolean checkRequirements() {
        return super.checkRequirements();
    }
}

