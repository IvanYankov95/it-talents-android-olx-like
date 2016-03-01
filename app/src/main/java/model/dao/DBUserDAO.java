package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.UserAcc;
import model.db.DatabaseHelper;

/**
 * Created by owner on 01/03/2016.
 */
public class DBUserDAO implements IUserDAO {

    private DatabaseHelper mDb;
    private Context context;

    public DBUserDAO(Context context){
        this.context = context;
        this.mDb = DatabaseHelper.getInstance(context);
    }

    @Override
    public long addUser(UserAcc user) {
        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mDb.USERNAME, user.getUserName());
        values.put(mDb.EMAIL, user.getEmail().toString());
        values.put(mDb.PASSWORD, user.getPassword());
        values.put(mDb.FIRST_NAME, user.getFirstName());
        values.put(mDb.LAST_NAME, user.getLastName());
        values.put(mDb.CITY, user.getCity());
        values.put(mDb.ADDRESS, user.getAddress());
        values.put(mDb.TELEPHONE, user.getPhoneNumber());

        long userId = db.insert(mDb.USERS, null, values);
        db.close();
        return userId;
    }

    @Override
    public UserAcc getUser(String username) {
        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mDb.USERS
                + "WHERE " + mDb.USERNAME + " = \"" + username + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String uname = c.getString(c.getColumnIndex(mDb.USERNAME));
        String password = c.getString(c.getColumnIndex(mDb.PASSWORD));
        String email = c.getString(c.getColumnIndex(mDb.EMAIL));
        String fname = c.getString(c.getColumnIndex(mDb.FIRST_NAME));
        String lname = c.getString(c.getColumnIndex(mDb.LAST_NAME));
        String city = c.getString(c.getColumnIndex(mDb.CITY));
        String address = c.getString(c.getColumnIndex(mDb.ADDRESS));
        String phone = c.getString(c.getColumnIndex(mDb.TELEPHONE));

        UserAcc user = new UserAcc(uname, password, email, fname, lname, city, address, phone);
        db.close();
        return user;
    }

    @Override
    public List<UserAcc> getAllUsers() {
        ArrayList<UserAcc> users = new ArrayList<UserAcc>();
        String query = "SELECT * FROM " + mDb.USERS;

        SQLiteDatabase db = mDb.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                String uname = c.getString(c.getColumnIndex(mDb.USERNAME));
                String password = c.getString(c.getColumnIndex(mDb.PASSWORD));
                String email = c.getString(c.getColumnIndex(mDb.EMAIL));
                String fname = c.getString(c.getColumnIndex(mDb.FIRST_NAME));
                String lname = c.getString(c.getColumnIndex(mDb.LAST_NAME));
                String city = c.getString(c.getColumnIndex(mDb.CITY));
                String address = c.getString(c.getColumnIndex(mDb.ADDRESS));
                String phone = c.getString(c.getColumnIndex(mDb.TELEPHONE));

                UserAcc user = new UserAcc(uname, password, email, fname, lname, city, address, phone);
                users.add(user);
            } while(c.moveToNext());
        }
        db.close();
        return users;
    }

    @Override
    public void deleteUser(UserAcc user) {
        SQLiteDatabase db = mDb.getWritableDatabase();
        db.delete(mDb.USERS, mDb.USERNAME + " = ?",
                new String[]{user.getUserName()});

        db.close();
    }

    @Override
    public long updateUser(UserAcc user) {
        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mDb.USERNAME, user.getUserName());
        values.put(mDb.EMAIL, user.getPhoneNumber());
        values.put(mDb.PASSWORD, user.getPassword());
        values.put(mDb.FIRST_NAME, user.getFirstName());
        values.put(mDb.LAST_NAME, user.getLastName());
        values.put(mDb.CITY, user.getCity());
        values.put(mDb.ADDRESS, user.getAddress());
        values.put(mDb.TELEPHONE, user.getPhoneNumber());

        long userId = db.update(mDb.USERS, values, mDb.USERNAME + " = ? ", new String[]{user.getUserName()});
        db.close();
        return userId;
    }

    @Override
    public boolean checkUsername(String username) {
        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.USERNAME + " = \"" + username + "\"";

        Cursor c = db.rawQuery(selectQuery, null);


        if (c != null && c.moveToFirst()) {
            db.close();
            return true;
        }
        else{
            db.close();
            return false;
        }
    }

    @Override
    public boolean checkUserEmail(String email) {
        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.EMAIL + " = \"" + email + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            db.close();
            return true;
        }
        else{
            db.close();
            return false;
        }
    }

    public UserAcc checkLogin (String email, String password) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.EMAIL + " = \"" + email
                + "\" AND " + mDb.PASSWORD + " = \"" + password + "\"";

        Cursor c = db.rawQuery(selectQuery, null);


        UserAcc user = null;

        if(c.moveToFirst()){
            long id = c.getLong(c.getColumnIndex(mDb.USER_ID));
            String uname = c.getString(c.getColumnIndex(mDb.USERNAME));
            String upassword = c.getString(c.getColumnIndex(mDb.PASSWORD));
            String uemail = c.getString(c.getColumnIndex(mDb.EMAIL));
            String fname = c.getString(c.getColumnIndex(mDb.FIRST_NAME));
            String lname = c.getString(c.getColumnIndex(mDb.LAST_NAME));
            String city = c.getString(c.getColumnIndex(mDb.CITY));
            String address = c.getString(c.getColumnIndex(mDb.ADDRESS));
            String phone = c.getString(c.getColumnIndex(mDb.TELEPHONE));

            user = new UserAcc(uemail, upassword, uname, fname, lname, phone, city, address);
            user.setUserId(id);
        }

        db.close();
        return user;
    }
}
