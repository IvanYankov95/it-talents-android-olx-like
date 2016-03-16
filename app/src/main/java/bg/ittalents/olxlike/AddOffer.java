package bg.ittalents.olxlike;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.Offer;
import model.OfferManager;
import model.UserAcc;
import model.UserManager;
import model.UserSessionManager;

public class AddOffer extends CustomActivityWithMenu implements View.OnClickListener {

    protected static final int IMAGE_GALLERY_REQUEST_1 = 21;
    protected static final int IMAGE_GALLERY_REQUEST_2 = 22;
    protected static final int IMAGE_GALLERY_REQUEST_3 = 23;
    protected static final int IMAGE_GALLERY_REQUEST_4 = 24;
    protected static final int IMAGE_GALLERY_REQUEST_5 = 25;
    protected static final int IMAGE_GALLERY_REQUEST_6 = 26;
    protected static final int IMAGE_GALLERY_REQUEST_7 = 27;
    protected static final int REQ_WIDTH = 500;
    protected static final int REQ_HEIGHT = 500;

    protected static ArrayList<byte[]> pictures;

    protected static boolean mainPictureCheck = false;

    protected static ImageButton mainPicture;
    protected static ImageButton picture1;
    protected static ImageButton picture2;
    protected static ImageButton picture3;
    protected static ImageButton picture4;
    protected static ImageButton picture5;
    protected static ImageButton picture6;
    protected static EditText title;
    protected static Spinner categorySpinner;
    protected static String selectedCategory;
    protected static EditText price;
    protected static RadioGroup condition;
    protected static EditText description;
    protected static EditText city;
    protected static Button addOfferButton;

    protected OfferManager offerManager;
    protected UserManager userManager;
    protected HashMap<String, String> user;
    protected long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        user = session.getUserDetails();
        offerManager = OfferManager.getInstance(this);
        userManager = UserManager.getInstance(this);

        pictures = new ArrayList<byte[]>();

        mainPicture = (ImageButton) findViewById(R.id.add_offer_main_picture);
        picture1 = (ImageButton) findViewById(R.id.add_offer_picture1);
        picture2 = (ImageButton) findViewById(R.id.add_offer_picture2);
        picture3 = (ImageButton) findViewById(R.id.add_offer_picture3);
        picture4 = (ImageButton) findViewById(R.id.add_offer_picture4);
        picture5 = (ImageButton) findViewById(R.id.add_offer_picture5);
        picture6 = (ImageButton) findViewById(R.id.add_offer_picture6);
        title = (EditText) findViewById(R.id.add_offer_title_text);
        categorySpinner = (Spinner) findViewById(R.id.add_offer_category_spinner);
        price = (EditText) findViewById(R.id.add_offer_price_text);
        condition = (RadioGroup) findViewById(R.id.add_offer_condition_radio);
        description = (EditText) findViewById(R.id.add_offer_description_text);
        city = (EditText) findViewById(R.id.add_offer_city_text);
        addOfferButton = (Button) findViewById(R.id.add_offer_add_button);

        List<String> categories = offerManager.getAllCategories();
        categories.add(0, "Select category");

        mainPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);

                photoPickerIntent.setDataAndType(data, "image/*");

                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST_1);

            }
        });

        picture1.setOnClickListener(this);
        picture2.setOnClickListener(this);
        picture3.setOnClickListener(this);
        picture4.setOnClickListener(this);
        picture5.setOnClickListener(this);
        picture6.setOnClickListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add offer
                if(checkRequirements(selectedCategory, R.id.add_offer_condition_radio)){
                    Date date = new Date();

                    RadioGroup rg = (RadioGroup)findViewById(R.id.add_offer_condition_radio);
                    String conditionString = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                    userId = Long.parseLong(user.get(session.KEY_ID));


                    Offer offer = new Offer(null, title.getText().toString(), description.getText().toString(),Double.parseDouble(price.getText().toString()), conditionString, selectedCategory, city.getText().toString(), true, pictures, date);


                    MyAssTask task = new MyAssTask();
                    task.execute(offer);
                }
            }
        });

    }

    protected boolean checkRequirements(String selectedCategory, int condition_radio){
        boolean titleCheck = false;
        boolean categoryCheck = false;
        boolean priceCheck = false;
        boolean conditionCheck = false;
        boolean descriptionCheck = false;
        boolean cityCheck = false;

        if(!mainPictureCheck)
            Toast.makeText(AddOffer.this, "Main picture is required.", Toast.LENGTH_SHORT).show();

        String titleString = title.getText().toString();
        if(titleString.isEmpty())
            title.setError("Title is required");
        else if(titleString.length() > 255)
            title.setError("Title is too long");
        else
            titleCheck = true;

        if(selectedCategory.equalsIgnoreCase("Select category"))
            Toast.makeText(AddOffer.this, "Please select category", Toast.LENGTH_SHORT).show();
        else
            categoryCheck = true;

        double priceDouble;
        if(!price.getText().toString().isEmpty())
            priceDouble = Double.parseDouble(price.getText().toString());
        else
            priceDouble = -1;

        if(price.getText().toString().isEmpty())
            price.setError("Price is required.");
        else if(priceDouble <=0)
            price.setError("Please enter valid price.");
        else
            priceCheck = true;

        String conditionString = "";
        if(condition.getCheckedRadioButtonId() != -1) {
            conditionCheck = true;
            RadioGroup rg = (RadioGroup)findViewById(condition_radio);
            conditionString = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        }
        else
            Toast.makeText(AddOffer.this, "Condition is required.", Toast.LENGTH_SHORT).show();

        String descriptionString = description.getText().toString();

        if(descriptionString.isEmpty())
            description.setError("Description is required");
        else if(descriptionString.length() < 100)
            description.setError("Description is too short");
        else if(descriptionString.length() > 2000)
            description.setError("Description is too long");
        else
            descriptionCheck = true;

        if(city.getText().toString().isEmpty())
            city.setError("City is required.");
        else
            cityCheck = true;

        if(mainPictureCheck && titleCheck && categoryCheck && priceCheck && conditionCheck && descriptionCheck && cityCheck)
            return true;
        else
            return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void notifyUserForNewOffer(long userID, long offerID) {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
        nBuilder.setSmallIcon(R.drawable.olx_small);
        nBuilder.setContentTitle("New Offer");
        UserAcc user = userManager.getUser(userID);
        String userName = user.getUserName();
        nBuilder.setContentText(userName + " added new offer.");
        nBuilder.setAutoCancel(true);

        Intent resultIntent = new Intent(this, OfferView.class);
        resultIntent.putExtra("idOffer", offerID);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(OfferView.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        nBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, nBuilder.build());





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){

            switch (requestCode){
                case IMAGE_GALLERY_REQUEST_1:
                    setPicture(mainPicture,data);
                    mainPictureCheck = true;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_offer_picture1:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_2);
                break;
            case R.id.add_offer_picture2:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_3);
                break;
            case R.id.add_offer_picture3:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_4);
                break;
            case R.id.add_offer_picture4:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_5);
                break;
            case R.id.add_offer_picture5:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_6);
                break;
            case R.id.add_offer_picture6:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_7);
                break;
        }
    }

    protected void askForPhotoWithIntent(int request){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, request);
    }

    protected void setPicture(ImageButton button, Intent data){
        Uri imageUrl = data.getData();

        InputStream inputStream = null;
        InputStream inputStream2 = null;
        ByteArrayOutputStream stream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageUrl);
            inputStream2 = getContentResolver().openInputStream(imageUrl);

            Bitmap image = decodeSampledBitmapFromStream(inputStream,inputStream2, REQ_WIDTH, REQ_HEIGHT);

            stream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            pictures.add(stream.toByteArray());

            button.setImageBitmap(image);

        } catch (FileNotFoundException e) {
            Toast.makeText(AddOffer.this, "Unable to open image", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception e){}
            try {
                if (inputStream2 != null)
                    inputStream2.close();
            } catch (Exception e){}
            try {
                if (stream != null)
                    stream.close();
            } catch (Exception e){}
        }
    }

    protected static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    protected static Bitmap decodeSampledBitmapFromStream(InputStream inputStream, InputStream inputStream2,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream2, null , options);
    }

    private class MyAssTask extends AsyncTask<Offer, Void, Long> {


        @Override
        protected Long doInBackground(Offer... params) {

            Offer offer = params[0];
            long offerID = offerManager.addOffer(offer, userId, selectedCategory);
            return offerID;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            if (aLong != -1) {
                Toast.makeText(AddOffer.this, "Offer added successfully", Toast.LENGTH_SHORT).show();
                notifyUserForNewOffer(userId, aLong);
                Intent offerViewIntent = new Intent(AddOffer.this, OfferView.class);
                offerViewIntent.putExtra("idOffer", aLong);
                startActivity(offerViewIntent);
            }
        }
    }
}
