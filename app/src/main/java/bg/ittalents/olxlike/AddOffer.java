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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddOffer extends AppCompatActivity {

    public static final int IMAGE_GALERY_REQUEST_1 = 20;
    Button mainPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        mainPicture = (Button) findViewById(R.id.add_offer_main_picture);

        mainPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);

                photoPickerIntent.setDataAndType(data, "image/*");

                startActivityForResult(photoPickerIntent, IMAGE_GALERY_REQUEST_1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_GALERY_REQUEST_1){
                Uri imageUrl = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imageUrl);

                    Bitmap image1 = BitmapFactory.decodeStream(inputStream);

                    Drawable drawable = new BitmapDrawable(getResources(), image1);

                    mainPicture.setBackgroundDrawable(drawable);
                    mainPicture.setText("");

                } catch (FileNotFoundException e) {
                    Toast.makeText(AddOffer.this, "Unable to open image", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
