package model.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bg.ittalents.olxlike.R;
import model.Message;
import model.Offer;
import model.UserAcc;

/**
 * Created by owner on 22/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    private Context context;


    // database name and version
    public static final String DATABASE_NAME = "OLXLIKE_DATABASE";
    public static final int DATABASE_VERSION = 4;

    // tables
    public static final String USERS = "users";
    public static final String OFFERS = "offers";
    public static final String MESSAGES = "messages";
    public static final String CATEGORIES = "categories";
    public static final String IMAGES = "images";

    //common fields
    public static final String CITY = "city";
    public static final String USER_ID = "user_id";
    public static final String OFFER_ID = "offer_id";
    public static final String CATEGORY_ID = "category_id";
    public static final String TITLE = "title";
    public  static final String CONTENT = "content";
    public  static final String DATE = "date";

    // USERS table colmns

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "fname";
    public static final String LAST_NAME = "lname";
    public static final String ADDRESS = "address";
    public static final String TELEPHONE = "phone";

    // OFFERS table colmns
    public static final String PRICE = "price";
    public static final String CONDITION = "condition";
    public static final String DESCRIPTION = "description";
    public static final String IS_ACTIVE = "is_active";

    // MESSAGES table columns
    public  static final String MESSAGE_ID = "message_id";
    public  static final String RECEIVER_ID = "receiver_id";
    public  static final String SENDER_ID = "sender_id";

    //IMAGES table columns
    public static final String IMAGE_ID = "image_id";
    public static final String IS_MAIN = "is_main";

    // CATEGORIES table columns
    public static final String NAME = "name";


    //Categories
    private static final String CLOTHES = "Clothes";
    private static final String ELECTRONIC = "Electronic";
    private static final String FURNITURE = "Furniture";
    private static final String SPORT = "Sport";
    private static final String MUSIC = "Music";
    private static final String BOOKS = "Books";
    private static final String ANIMALS = "Animals";
    private static final String SHOES = "Shoes";
    private static final String COSMETICS = "Cosmetics";
    private static final String ACCESSORIES = "Accessories";
    private static final String AUTO = "Auto";

    // CREATE statements

    // USERS CREATE statement
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + USERS + " ("
            + USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "
            + USERNAME + " VARCHAR(255) NOT NULL, "
            + EMAIL + " VARCHAR (255) NOT NULL, "
            + PASSWORD + " VARCHAR(255) NOT NULL, "
            + FIRST_NAME + " VARCHAR (255), "
            + LAST_NAME + " VARCHAR(255), "
            + CITY + " VARCHAR(255), "
            + ADDRESS + " VARCHAR(255), "
            + TELEPHONE + " VARCHAR(255)"
            +") ";

    private static final String CREATE_OFFERS_TABLE = "CREATE TABLE IF NOT EXISTS " +OFFERS + " ("
            + OFFER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_ID + " INTEGER, "
            + CATEGORY_ID + " INTEGER, "
            + TITLE + " VARCHAR(255) NOT NULL, "
            + PRICE + " DECIMAL (10,2) NOT NULL, "
            + CONDITION + " VARCHAR(255) NOT NULL, "
            + CITY + " VARCHAR(255) NOT NULL, "
            + DESCRIPTION + " VARCHAR(2000) NOT NULL, "
            + DATE + " DATE NOT NULL, "
            + IS_ACTIVE + " BOOL NOT NULL, "
            + "FOREIGN KEY ("+ USER_ID +") REFERENCES "+ USERS +"("+ USER_ID+")"
            + "FOREIGN KEY ("+ CATEGORY_ID +") REFERENCES "+ CATEGORIES +"("+ CATEGORY_ID +")"
            +") ";

    private static final String CREATE_MESSAGES_TABLE = "CREATE TABLE IF NOT EXISTS " +MESSAGES + " ("
            + MESSAGE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RECEIVER_ID + " INTEGER, "
            + SENDER_ID + " INTEGER, "
            + TITLE + " VARCHAR(255) NOT NULL, "
            + CONTENT + " VARCHAR(3000) NOT NULL, "
            + DATE + " DATETIME NOT NULL, "
            + "FOREIGN KEY ("+ RECEIVER_ID +") REFERENCES "+ USERS +"("+ USER_ID+")"
            + "FOREIGN KEY ("+ SENDER_ID +") REFERENCES "+ USERS +"("+ USER_ID +")"
            +") ";

    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE IF NOT EXISTS " +CATEGORIES + " ("
            + CATEGORY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " VARCHAR(255) NOT NULL "
            +") ";

    private static final String CREATE_IMAGES_TABLE =  "CREATE TABLE IF NOT EXISTS " +IMAGES + " ("
            + IMAGE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + OFFER_ID + " INTEGER, "
            + CONTENT + " BLOB NOT NULL, "
            + IS_MAIN + " BOOL NOT NULL, "
            + "FOREIGN KEY ("+ OFFER_ID +") REFERENCES "+ OFFERS +"("+ OFFER_ID+")"
            +") ";



    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static DatabaseHelper getInstance(Context context){
        if(instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        // creating required tables
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_OFFERS_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_MESSAGES_TABLE);
        db.execSQL(CREATE_IMAGES_TABLE);
        addCategories(db);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        db.execSQL("DROP TABLE IF EXISTS " + OFFERS);
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + IMAGES);

        //create new tables
        onCreate(db);

    }

    public void addCategories(SQLiteDatabase db){
        ArrayList<String> cat = new ArrayList<String>();
        cat.add(ELECTRONIC);
        cat.add(FURNITURE);
        cat.add(CLOTHES);
        cat.add(SPORT);
        cat.add(MUSIC);
        cat.add(BOOKS);
        cat.add(ANIMALS);
        cat.add(COSMETICS);
        cat.add(ACCESSORIES);
        cat.add(SHOES);
        cat.add(AUTO);

        for(String c : cat){
            ContentValues vals = new ContentValues();
            vals.put(NAME, c);
            db.insert(CATEGORIES, null, vals);
        }

    }

//    private byte[] convertImage(int id){
//        Resources res = context.getResources();
//        Drawable drawable = ResourcesCompat.getDrawable(res, id, null);
//        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] bitMapData = stream.toByteArray();
//
//        return bitMapData;
//    }

    // Messages CRUD
    //Create message
    public long createMessage(UserAcc sender, UserAcc receiver, Message msg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SENDER_ID, sender.getUserId());
        values.put(RECEIVER_ID, receiver.getUserId());
        values.put(TITLE, msg.getHeading());
        values.put(CONTENT, msg.getText());
        values.put(DATE, String.valueOf(msg.getDate()));

        long msgId = db.insert(MESSAGES, null, values);
        return msgId;
    }
    // more for messages
    // ............


    //IMAGES
    //create image
    public long createImage(byte[] array, int offerId){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(OFFER_ID, offerId);
        values.put(CONTENT, array);

        long id = db.insert(IMAGES, null, values);
        return id;
    }

    public boolean checkCategory(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + CATEGORIES
                + " WHERE " + NAME + " = " + name;

        Cursor c = db.rawQuery(query, null);
        if(c != null)
            return true;
        else
            return false;

    }





    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
