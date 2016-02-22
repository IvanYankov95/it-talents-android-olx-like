package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    // TODO WRITE CREATE OFFERS, MESSAGES AND IMAGES STATEMENTS




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_USERS_TABLE);

        //TODO EXECUTE OTHER TABLES
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + USERS);

        // TODO DROP OTHER TABLES
    }

    // putting new row in USERS table
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

    public boolean selectUsername(String username){
        //TODO SOME LOGIC HERE
        //if user exist return true, else return false
        return true;
    }

    public boolean selectUserEmail(String email){
        //TODO SELECT QUERY
        //if email exist return true, else return false
        return true;
    }




    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
