package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.UserAcc;

/**
 * Created by owner on 22/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    // database name and version
    private static final String DATABASE_NAME = "OLXLIKE_DATABASE";
    private static final int DATABASE_VERSION = 1;

    // tables
    private static final String USERS = "users";
    private static final String OFFERS = "offers";
    private static final String MESSAGES = "messages";
    private static final String CATEGORIES = "categories";
    private static final String IMAGES = "images";

    //common fields
    private static final String CITY = "city";
    private static final String USER_ID = "user_id";
    private static final String OFFER_ID = "offer_id";
    private static final String CATEGORY_ID = "category_id";
    private static final String TITLE = "title";
    private  static final String CONTENT = "content";
    private  static final String DATE = "date";

    // USERS table colmns

    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "fname";
    private static final String LAST_NAME = "lname";
    private static final String ADDRESS = "address";
    private static final String TELEPHONE = "phone";

    // OFFERS table colmns
    private static final String PRICE = "price";
    private static final String CONDITION = "condition";
    private static final String DESCRIPTION = "description";
    private static final String IS_ACTIVE = "is_active";

    // MESSAGES table columns
    private  static final String MESSAGE_ID = "message_id";
    private  static final String RECEIVER_ID = "receiver_id";
    private  static final String SENDER_ID = "sender_id";

    // CATEGORIES table columns
    private static final String NAME = "name";

    //IMAGES table columns
    private static final String IMAGE_ID = "image_id";

    // CREATE statements

    // USERS CREATE statement
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + USERS + " ("
            + USER_ID +" INTEGER PRIMARY KEY AUTO_INCREMENT, "
            + USERNAME + " VARCHAR(255) NOT NULL, "
            + EMAIL + " VARCHAR 255 NOT NULL, "
            + PASSWORD + " VARCHAR(255) NOT NULL, "
            + FIRST_NAME + " VARCHAR (255), "
            + LAST_NAME + " VARCHAR(255), "
            + CITY + " VARCHAR(255), "
            + ADDRESS + " VARCHAR(255), "
            + TELEPHONE + " VARCHAR(255)"
            +") ";

    private static final String CREATE_OFFERS_TABLE = "CREATE TABLE " +OFFERS + " ("
            + OFFER_ID +" INTEGER PRIMARY KEY AUTO_INCREMENT, "
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

    private static final String CREATE_MESSAGES_TABLE = "CREATE TABLE " +MESSAGES + " ("
            + MESSAGE_ID +" INTEGER PRIMARY KEY AUTO_INCREMENT, "
            + RECEIVER_ID + " INTEGER, "
            + SENDER_ID + " INTEGER, "
            + TITLE + " VARCHAR(255) NOT NULL, "
            + CONTENT + " VARCHAR(3000) NOT NULL, "
            + DATE + " DATETIME NOT NULL, "
            + "FOREIGN KEY ("+ RECEIVER_ID +") REFERENCES "+ USERS +"("+ USER_ID+")"
            + "FOREIGN KEY ("+ SENDER_ID +") REFERENCES "+ USERS +"("+ USER_ID +")"
            +") ";

    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " +CATEGORIES + " ("
            + CATEGORY_ID +" INTEGER PRIMARY KEY AUTO_INCREMENT, "
            + NAME + " VARCHAR(255) NOT NULL, "
            +") ";

    private static final String CREATE_IMAGES_TABLE =  "CREATE TABLE " +IMAGES + " ("
            + IMAGE_ID +" INTEGER PRIMARY KEY AUTO_INCREMENT, "
            + OFFER_ID + " INTEGER, "
            + CONTENT + " BLOB NOT NULL, "
            + "FOREIGN KEY ("+ OFFER_ID +") REFERENCES "+ OFFERS +"("+ OFFER_ID+")"
            +") ";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    // CRUD User

    // create user
    public long createUser(UserAcc user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUserName());
        values.put(EMAIL, user.getPhoneNumber());
        values.put(PASSWORD, user.getPassword());
        values.put(FIRST_NAME, user.getFirstName());
        values.put(LAST_NAME, user.getLastName());
        values.put(CITY, user.getCity());
        values.put(ADDRESS, user.getAddress());
        values.put(TELEPHONE, user.getPhoneNumber());

        long userId = db.insert(USERS, null, values);
        return userId;
    }

    //read single user by username
    public UserAcc selectUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USERS
                + "WHERE " + USERNAME + " = " + username;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String uname = c.getString(c.getColumnIndex(USERNAME));
        String password = c.getString(c.getColumnIndex(PASSWORD));
        String email = c.getString(c.getColumnIndex(EMAIL));
        String fname = c.getString(c.getColumnIndex(FIRST_NAME));
        String lname = c.getString(c.getColumnIndex(LAST_NAME));
        String city = c.getString(c.getColumnIndex(CITY));
        String address = c.getString(c.getColumnIndex(ADDRESS));
        String phone = c.getString(c.getColumnIndex(TELEPHONE));

        UserAcc user = new UserAcc(uname, password, email, fname, lname, city, address, phone);

        return user;
    }

    // get all users
    public ArrayList<UserAcc> getAllUsers(){
        ArrayList<UserAcc> users = new ArrayList<UserAcc>();
        String query = "SELECT * FROM " + USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                String uname = c.getString(c.getColumnIndex(USERNAME));
                String password = c.getString(c.getColumnIndex(PASSWORD));
                String email = c.getString(c.getColumnIndex(EMAIL));
                String fname = c.getString(c.getColumnIndex(FIRST_NAME));
                String lname = c.getString(c.getColumnIndex(LAST_NAME));
                String city = c.getString(c.getColumnIndex(CITY));
                String address = c.getString(c.getColumnIndex(ADDRESS));
                String phone = c.getString(c.getColumnIndex(TELEPHONE));

                UserAcc user = new UserAcc(uname, password, email, fname, lname, city, address, phone);
                users.add(user);
            } while(c.moveToNext());
        }

        return users;
    }

    //update user
    public long updateUsr(UserAcc user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUserName());
        values.put(EMAIL, user.getPhoneNumber());
        values.put(PASSWORD, user.getPassword());
        values.put(FIRST_NAME, user.getFirstName());
        values.put(LAST_NAME, user.getLastName());
        values.put(CITY, user.getCity());
        values.put(ADDRESS, user.getAddress());
        values.put(TELEPHONE, user.getPhoneNumber());

        long userId = db.update(USERS, values, USERNAME + " = ? ", new String[]{user.getUserName()});
        return userId;
    }


    //deleting user

    public void deleteUser(UserAcc user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USERS, USERNAME + " = ?",
                new String[] { user.getUserName() });
    }


    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USERS
                + "WHERE " + USERNAME + " = " + username;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            return true;
        else
            return false;
    }

    public boolean checkUserEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USERS
                + "WHERE " + EMAIL + " = " + email;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
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
