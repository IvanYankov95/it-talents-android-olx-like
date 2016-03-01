package bg.ittalents.olxlike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddOffer extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST_1 = 20;

    private boolean mainPictureCheck = false;
    
    private static ImageButton mainPicture;
    private static EditText title;
    private static Spinner categorySpinner;
    private static String selectedCategory;
    private static EditText price;
    private static RadioGroup condition;
    private static EditText description;
    private static EditText city;
    private static Button addOfferButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        mainPicture = (ImageButton) findViewById(R.id.add_offer_main_picture);
        title = (EditText) findViewById(R.id.add_offer_title_text);
        categorySpinner = (Spinner) findViewById(R.id.add_offer_category_spinner);
        price = (EditText) findViewById(R.id.add_offer_price_text);
        condition = (RadioGroup) findViewById(R.id.add_offer_condition_radio);
        description = (EditText) findViewById(R.id.add_offer_description_text);
        city = (EditText) findViewById(R.id.add_offer_city_text);
        addOfferButton = (Button) findViewById(R.id.add_offer_add_button);

        List<String> categories = new ArrayList<String>();
        categories.add("Select category");
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

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

        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
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

                int priceInt;
                if(!price.getText().toString().isEmpty())
                    priceInt = Integer.parseInt(price.getText().toString());
                else
                    priceInt = -1;

                if(price.getText().toString().isEmpty())
                    price.setError("Price is required.");
                else if(priceInt <=0)
                    price.setError("Please enter valid price.");
                else
                    priceCheck = true;

                if(condition.getCheckedRadioButtonId() != -1)
                    conditionCheck = true;
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

                if(mainPictureCheck && titleCheck && categoryCheck && priceCheck && conditionCheck && descriptionCheck && cityCheck){
                    Toast.makeText(AddOffer.this, "Done", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            mainPictureCheck = true;
            if(requestCode == IMAGE_GALLERY_REQUEST_1){
                Uri imageUrl = data.getData();

                InputStream inputStream = null;

                try {
                    inputStream = getContentResolver().openInputStream(imageUrl);

                    Bitmap image1 = BitmapFactory.decodeStream(inputStream);

                    Drawable drawable = new BitmapDrawable(getResources(), image1);

                    mainPicture.setImageDrawable(drawable);

                } catch (FileNotFoundException e) {
                    Toast.makeText(AddOffer.this, "Unable to open image", Toast.LENGTH_SHORT).show();
                } finally {
                    try {
                        if (inputStream != null)
                            inputStream.close();
                    } catch (Exception e){

                    }
                }

            }
        }
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
                startActivity(new Intent(AddOffer.this, AddOffer.class));
                break;
            case R.id.action_home:
                startActivity(new Intent(AddOffer.this, Home.class));
                break;
            case R.id.action_messages:
                startActivity(new Intent(AddOffer.this, MyMessages.class));
                break;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                startActivity(new Intent(AddOffer.this, ViewUser.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(AddOffer.this, Settings.class));
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
