package model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.Message;
import model.Offer;
import model.UserAcc;

/**
 * Created by owner on 22/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;


    // database name and version
    public static final String DATABASE_NAME = "OLXLIKE_DATABASE";
    public static final int DATABASE_VERSION = 1;

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

    // CATEGORIES table columns
    public static final String NAME = "name";

    //IMAGES table columns
    public static final String IMAGE_ID = "image_id";
    public static final String IS_MAIN = "is_main";

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
            + DATE + " DATETIME NOT NULL, "
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
            + IS_ACTIVE + " BOOL NOT NULL, "
            + "FOREIGN KEY ("+ OFFER_ID +") REFERENCES "+ OFFERS +"("+ OFFER_ID+")"
            +") ";




    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

//    // create user
//    public long createUser(UserAcc user){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(USERNAME, user.getUserName());
//        values.put(EMAIL, user.getPhoneNumber());
//        values.put(PASSWORD, user.getPassword());
//        values.put(FIRST_NAME, user.getFirstName());
//        values.put(LAST_NAME, user.getLastName());
//        values.put(CITY, user.getCity());
//        values.put(ADDRESS, user.getAddress());
//        values.put(TELEPHONE, user.getPhoneNumber());
//
//        long userId = db.insert(USERS, null, values);
//        db.close();
//        return userId;
//    }

    //OFFERS

    public long createOffer(Offer offer, int userId, int categoryId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, userId);
        values.put(CATEGORY_ID, categoryId);
        values.put(TITLE, offer.getName());
        values.put(PRICE, offer.getPrice());
        values.put(CONDITION, String.valueOf(offer.getCondition()));
        values.put(DESCRIPTION, offer.getDescription());
        values.put(CITY, offer.getCity());
        values.put(IS_ACTIVE, String.valueOf(offer.isActive()));
        values.put(DATE, String.valueOf(offer.getCreationDate()));

        long id = db.insert(OFFERS, null, values);
        db.close();
        return id;
    }

    // Messages CRUD
    //Create message
    public long creaeMessage(UserAcc sender, UserAcc receiver, Message msg){
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
    // Categories CRUD

    //create category
    public long addCategory(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);

        long id =  db.insert(CATEGORIES, null, values);
        db.close();
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

//    //read single user by username
//    public UserAcc selectUser(String username){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selectQuery = "SELECT * FROM " + USERS
//                + "WHERE " + USERNAME + " = " + username;
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            c.moveToFirst();
//
//        String uname = c.getString(c.getColumnIndex(USERNAME));
//        String password = c.getString(c.getColumnIndex(PASSWORD));
//        String email = c.getString(c.getColumnIndex(EMAIL));
//        String fname = c.getString(c.getColumnIndex(FIRST_NAME));
//        String lname = c.getString(c.getColumnIndex(LAST_NAME));
//        String city = c.getString(c.getColumnIndex(CITY));
//        String address = c.getString(c.getColumnIndex(ADDRESS));
//        String phone = c.getString(c.getColumnIndex(TELEPHONE));
//
//        UserAcc user = new UserAcc(uname, password, email, fname, lname, city, address, phone);
//        db.close();
//        return user;
//    }
//
//    // get all users
//    public ArrayList<UserAcc> getAllUsers(){
//        ArrayList<UserAcc> users = new ArrayList<UserAcc>();
//        String query = "SELECT * FROM " + USERS;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(query, null);
//
//        if(c.moveToFirst()){
//            do{
//                String uname = c.getString(c.getColumnIndex(USERNAME));
//                String password = c.getString(c.getColumnIndex(PASSWORD));
//                String email = c.getString(c.getColumnIndex(EMAIL));
//                String fname = c.getString(c.getColumnIndex(FIRST_NAME));
//                String lname = c.getString(c.getColumnIndex(LAST_NAME));
//                String city = c.getString(c.getColumnIndex(CITY));
//                String address = c.getString(c.getColumnIndex(ADDRESS));
//                String phone = c.getString(c.getColumnIndex(TELEPHONE));
//
//                UserAcc user = new UserAcc(uname, password, email, fname, lname, city, address, phone);
//                users.add(user);
//            } while(c.moveToNext());
//        }
//        db.close();
//        return users;
//    }
//
//    //update user
//    public long updateUsr(UserAcc user){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(USERNAME, user.getUserName());
//        values.put(EMAIL, user.getPhoneNumber());
//        values.put(PASSWORD, user.getPassword());
//        values.put(FIRST_NAME, user.getFirstName());
//        values.put(LAST_NAME, user.getLastName());
//        values.put(CITY, user.getCity());
//        values.put(ADDRESS, user.getAddress());
//        values.put(TELEPHONE, user.getPhoneNumber());
//
//        long userId = db.update(USERS, values, USERNAME + " = ? ", new String[]{user.getUserName()});
//        db.close();
//        return userId;
//    }
//
//
//    //deleting user
//
//    public void deleteUser(UserAcc user){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(USERS, USERNAME + " = ?",
//                new String[]{user.getUserName()});
//    }


//    public boolean checkUsername(String username){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selectQuery = "SELECT * FROM " + USERS
//                + "WHERE " + USERNAME + " = " + username;
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            return true;
//        else
//            return false;
//    }

//    public boolean checkUserEmail(String email){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selectQuery = "SELECT * FROM " + USERS
//                + "WHERE " + EMAIL + " = " + email;
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            return true;
//        else
//            return false;
//    }



    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
