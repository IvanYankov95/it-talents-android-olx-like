package bg.ittalents.olxlike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Offer;
import model.OfferManager;

public class EditOffer extends AddOffer implements View.OnClickListener {

    private OfferManager manager = OfferManager.getInstance(this);

    private static CheckBox archiveBox;

    private static boolean isArchived = false;

    private static String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_offer);

        archiveBox = (CheckBox) findViewById(R.id.edit_offer_archive_checkbox);

        mainPicture = (ImageButton) findViewById(R.id.edit_offer_main_picture);
        picture1 = (ImageButton) findViewById(R.id.edit_offer_picture1);
        picture2 = (ImageButton) findViewById(R.id.edit_offer_picture2);
        picture3 = (ImageButton) findViewById(R.id.edit_offer_picture3);
        picture4 = (ImageButton) findViewById(R.id.edit_offer_picture4);
        picture5 = (ImageButton) findViewById(R.id.edit_offer_picture5);
        picture6 = (ImageButton) findViewById(R.id.edit_offer_picture6);
        title = (EditText) findViewById(R.id.edit_offer_title_text);
        categorySpinner = (Spinner) findViewById(R.id.edit_offer_category_spinner);
        price = (EditText) findViewById(R.id.edit_offer_price_text);
        condition = (RadioGroup) findViewById(R.id.edit_offer_condition_radio);
        description = (EditText) findViewById(R.id.edit_offer_description_text);
        city = (EditText) findViewById(R.id.edit_offer_city_text);
        addOfferButton = (Button) findViewById(R.id.edit_offer_save_button);

        Bundle bundle = getIntent().getExtras();
        final long id = bundle.getLong("idOffer");
        final Offer offer = manager.getOfferByID(id);
        ArrayList<byte[]> images = offer.getImages();
        pictures = images;

        mainPictureCheck = true;
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

        List<String> categories = offerManager.getAllCategories();
        categories.add(0, "Select category");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

        categorySpinner.setSelection(((ArrayAdapter) categorySpinner.getAdapter()).getPosition(offer.getCategory()));

        price.setText(String.valueOf(offer.getPrice()));

        String cn = offer.getCondition();
        if(cn.equalsIgnoreCase("new"))
            condition.check(R.id.edit_offer_radioButton);
        else
            condition.check(R.id.edit_offer_radioButton2);

        description.setText(offer.getDescription());

        city.setText(offer.getCity());

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        picture1.setOnClickListener(this);
        picture2.setOnClickListener(this);
        picture3.setOnClickListener(this);
        picture4.setOnClickListener(this);
        picture5.setOnClickListener(this);
        picture6.setOnClickListener(this);

        addOfferButton.setText("Save edit");
        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRequirements(selectedCategory, R.id.edit_offer_condition_radio)){
                    Date date = new Date();

                    RadioGroup rg = (RadioGroup)findViewById(R.id.edit_offer_condition_radio);
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
    protected boolean checkRequirements(String selectedCategory, int condition_radio) {
        return super.checkRequirements(selectedCategory, condition_radio);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_offer_picture1:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_2);
                break;
            case R.id.edit_offer_picture2:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_3);
                break;
            case R.id.edit_offer_picture3:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_4);
                break;
            case R.id.edit_offer_picture4:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_5);
                break;
            case R.id.edit_offer_picture5:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_6);
                break;
            case R.id.edit_offer_picture6:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_7);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){

            switch (requestCode){
                case IMAGE_GALLERY_REQUEST_1:
                    setPicture(mainPicture,data);
                    break;
                case IMAGE_GALLERY_REQUEST_2:
                    setPicture(picture1,data);
                    break;
                case IMAGE_GALLERY_REQUEST_3:
                    setPicture(picture2,data);
                    break;
                case IMAGE_GALLERY_REQUEST_4:
                    setPicture(picture3,data);
                    break;
                case IMAGE_GALLERY_REQUEST_5:
                    setPicture(picture4,data);
                    break;
                case IMAGE_GALLERY_REQUEST_6:
                    setPicture(picture5,data);
                    break;
                case IMAGE_GALLERY_REQUEST_7:
                    setPicture(picture6,data);
                    break;

            }
        }
    }
}

