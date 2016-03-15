package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import model.Offer;
import model.UserAcc;
import model.db.DatabaseHelper;

/**
 * Created by owner on 01/03/2016.
 */
public class DBOfferDAO implements IOfferDAO {

    private DatabaseHelper mDb;
    private DBUserDAO userDAO;

    public DBOfferDAO(Context context){
        this.mDb = DatabaseHelper.getInstance(context);
        this.userDAO = new DBUserDAO(context);
    }

    @Override
    public long addOffer(Offer offer, long userId, String category) {
        SQLiteDatabase db = mDb.getWritableDatabase();

        long categoryId = getCategory(category);

        ContentValues values = new ContentValues();
        values.put(mDb.USER_ID, userId);
        values.put(mDb.CATEGORY_ID, categoryId);
        values.put(mDb.TITLE, offer.getName());
        values.put(mDb.PRICE, offer.getPrice());
        values.put(mDb.CONDITION, String.valueOf(offer.getCondition()));
        values.put(mDb.DESCRIPTION, offer.getDescription());
        values.put(mDb.CITY, offer.getCity());
        values.put(mDb.IS_ACTIVE, String.valueOf(offer.isActive()));
        values.put(mDb.DATE, String.valueOf(offer.getCreationDate()));

        long id = db.insert(mDb.OFFERS, null, values);

        ArrayList<byte[]> images = offer.getImages();
        for(int i=0; i<images.size(); i++){
            ContentValues vals = new ContentValues();
            vals.put(mDb.OFFER_ID, id);
            vals.put(mDb.CONTENT, images.get(i));
            if(i!=0)
                vals.put(mDb.IS_MAIN, false);
            else
                vals.put(mDb.IS_MAIN, true);

            db.insert(mDb.IMAGES, null, vals);
        }

        db.close();

        offer.setId(id);
        return id;
    }

    //get category by ID
    @Override
    public String getCategory(long id) {
        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT " + mDb.NAME + " FROM " + mDb.CATEGORIES
                + " WHERE " + mDb.CATEGORY_ID + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);
        String name = "";
        if(c.moveToFirst()){
            name = c.getString(c.getColumnIndex(mDb.NAME));
        }
        c.close();
        db.close();
        return name;
    }

    // get category by name
    @Override
    public long getCategory(String name) {
        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT " + mDb.CATEGORY_ID + " FROM " + mDb.CATEGORIES
                + " WHERE " + mDb.NAME + " = \"" + name + "\"";
        Cursor c = db.rawQuery(selectQuery, null);
        long id = 0;
        if(c.moveToFirst()){
            id = c.getLong(c.getColumnIndex(mDb.CATEGORY_ID));
        }
        c.close();
        return id;
    }

    // get offer by ID
    @Override
    public Offer getOffer(long id) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.OFFERS
                + " WHERE " + mDb.OFFER_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);
        Offer offer = null;
        if(c.moveToFirst()){
            long userId = c.getLong(c.getColumnIndex(mDb.USER_ID));
            long catId = c.getLong(c.getColumnIndex(mDb.CATEGORY_ID));
            String title = c.getString(c.getColumnIndex(mDb.TITLE));
            double price = c.getDouble(c.getColumnIndex(mDb.PRICE));
            String condition = c.getString(c.getColumnIndex(mDb.CONDITION));
            String description = c.getString(c.getColumnIndex(mDb.DESCRIPTION));
            String city = c.getString(c.getColumnIndex(mDb.CITY));
            boolean active = Boolean.parseBoolean(c.getString(c.getColumnIndex(mDb.IS_ACTIVE)));
            String date = c.getString(c.getColumnIndex(mDb.DATE));

        /*    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
            Date creationDate = new Date();
            try {
                creationDate = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

            UserAcc user = userDAO.getUser(userId);
            String category = getCategory(catId);
            ArrayList<byte[]> images = getImages(id);

            offer = new Offer(user, title, description, price, condition, category, city, active, images, null);

        }
        c.close();
        db.close();
        return offer;
    }


    // get images by offer id
    @Override
    public ArrayList<byte[]> getImages(long offerId) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.IMAGES
                + " WHERE " + mDb.OFFER_ID + " = " + offerId;

        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<byte[]> images = new ArrayList<byte[]>();

        if(c.moveToFirst()){
            do{
                byte[] content = c.getBlob(c.getColumnIndex(mDb.CONTENT));

                images.add(content);
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return images;
    }

    @Override
    public ArrayList<Offer> getOffersByCategory(String category) {
        SQLiteDatabase db = mDb.getReadableDatabase();
        long catId = getCategory(category);
        String selectQuery = "SELECT * FROM " + mDb.OFFERS
                + " WHERE " + mDb.CATEGORY_ID + " = " + catId;
        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<Offer> offers = new ArrayList<Offer>();

        if(c.moveToFirst()){
            do {
                long offerId = c.getLong(c.getColumnIndex(mDb.OFFER_ID));
                long userId = c.getLong(c.getColumnIndex(mDb.USER_ID));
                String title = c.getString(c.getColumnIndex(mDb.TITLE));
                double price = c.getDouble(c.getColumnIndex(mDb.PRICE));
                String condition = c.getString(c.getColumnIndex(mDb.CONDITION));
                String description = c.getString(c.getColumnIndex(mDb.DESCRIPTION));
                String city = c.getString(c.getColumnIndex(mDb.CITY));
                boolean active = Boolean.parseBoolean(c.getString(c.getColumnIndex(mDb.IS_ACTIVE)));
                String date = c.getString(c.getColumnIndex(mDb.DATE));

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date creationDate = new Date();
                try {
                    creationDate = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                UserAcc user = userDAO.getUser(userId);
                ArrayList<byte[]> images = getImages(offerId);

                Offer offer = new Offer(user, title, description, price, condition, category, city, active, images, creationDate);
                offer.setId(offerId);
                offers.add(offer);
            }
            while(c.moveToNext());
        }

        c.close();
        db.close();
        return offers;
    }

    @Override
    public void deleteOffer(Offer offer) {

    }

    @Override
    public ArrayList<String> getAllCategories() {

        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mDb.CATEGORIES;
        Cursor c = db.rawQuery(selectQuery, null);

        ArrayList<String> categories = new ArrayList<String>();

        if(c.moveToFirst()) {
            do {
                String name = c.getString(c.getColumnIndex(mDb.NAME));
                categories.add(name);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return categories;
    }

    // get offer by word in title
    public ArrayList<Offer> getOffers(String word){
        ArrayList<Offer> offers = new ArrayList<Offer>();

        SQLiteDatabase db = mDb.getReadableDatabase();
        String query = "SELECT * FROM " + mDb.OFFERS
                + " WHERE " + mDb.TITLE + " LIKE \"%" + word + "%\"";
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do {
                long offerId = c.getLong(c.getColumnIndex(mDb.OFFER_ID));
                long userId = c.getLong(c.getColumnIndex(mDb.USER_ID));
                long catId = c.getLong(c.getColumnIndex(mDb.CATEGORY_ID));
                String title = c.getString(c.getColumnIndex(mDb.TITLE));
                double price = c.getDouble(c.getColumnIndex(mDb.PRICE));
                String condition = c.getString(c.getColumnIndex(mDb.CONDITION));
                String description = c.getString(c.getColumnIndex(mDb.DESCRIPTION));
                String city = c.getString(c.getColumnIndex(mDb.CITY));
                boolean active = Boolean.parseBoolean(c.getString(c.getColumnIndex(mDb.IS_ACTIVE)));
                String date = c.getString(c.getColumnIndex(mDb.DATE));

                //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                //Date creationDate = new Date();
                //try {
                //    creationDate = sdf.parse(date);
                //} catch (ParseException e) {
                //    e.printStackTrace();
                //}

                UserAcc user = userDAO.getUser(userId);
                String category = getCategory(catId);
                ArrayList<byte[]> images = getImages(offerId);

                Offer offer = new Offer(user, title, description, price, condition, category, city, active, images, null);
                offer.setId(offerId);
                offers.add(offer);
            }
            while(c.moveToNext());
        }

        c.close();
        db.close();
        return offers;
    }

    @Override
    public ArrayList<Offer> getOffersByUser(long userId) {
        ArrayList<Offer> offers = new ArrayList<Offer>();

        SQLiteDatabase db = mDb.getReadableDatabase();
        String query = "SELECT * FROM " + mDb.OFFERS
                + " WHERE " + mDb.USER_ID + " = " + userId;
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do {
                long offerId = c.getLong(c.getColumnIndex(mDb.OFFER_ID));
                long catId = c.getLong(c.getColumnIndex(mDb.CATEGORY_ID));
                String title = c.getString(c.getColumnIndex(mDb.TITLE));
                double price = c.getDouble(c.getColumnIndex(mDb.PRICE));
                String condition = c.getString(c.getColumnIndex(mDb.CONDITION));
                String description = c.getString(c.getColumnIndex(mDb.DESCRIPTION));
                String city = c.getString(c.getColumnIndex(mDb.CITY));
                boolean active = Boolean.parseBoolean(c.getString(c.getColumnIndex(mDb.IS_ACTIVE)));
                String date = c.getString(c.getColumnIndex(mDb.DATE));

//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//                Date creationDate = new Date();
//                try {
//                    creationDate = sdf.parse(date);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

                UserAcc user = userDAO.getUser(userId);
                String category = getCategory(catId);
                ArrayList<byte[]> images = getImages(offerId);

                Offer offer = new Offer(user, title, description, price, condition, category, city, active, images, null);
                offer.setId(offerId);
                offers.add(offer);
            }
            while(c.moveToNext());
        }

        c.close();
        db.close();
        return offers;
    }

    public int getOffersCount(){
        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mDb.OFFERS;
        Cursor c = db.rawQuery(selectQuery, null);
        int count = c.getCount();
        c.close();
        return count;
    }

    @Override
    public long updateOffer(long offerId, Offer offer) {
        SQLiteDatabase db = mDb.getWritableDatabase();

        long categoryId = getCategory(offer.getCategory());

        ContentValues values = new ContentValues();
        values.put(mDb.CATEGORY_ID, categoryId);
        values.put(mDb.TITLE, offer.getName());
        values.put(mDb.PRICE, offer.getPrice());
        values.put(mDb.CONDITION, String.valueOf(offer.getCondition()));
        values.put(mDb.DESCRIPTION, offer.getDescription());
        values.put(mDb.CITY, offer.getCity());
        values.put(mDb.IS_ACTIVE, String.valueOf(offer.isActive()));
        values.put(mDb.DATE, String.valueOf(offer.getCreationDate()));

        long id = db.update(mDb.OFFERS, values, mDb.OFFER_ID +" = ? ", new String[]{String.valueOf(offerId)});

        ArrayList<byte[]> newImages = offer.getImages();
        ArrayList<byte[]> oldImages = getImages(offerId);

        for(int i = 0; i < oldImages.size(); i++){
            boolean equals = false;
            for(int j = 0; j < newImages.size(); j++){
                if(Arrays.equals(oldImages.get(i), newImages.get(j))){
                    oldImages.remove(i);
                    newImages.remove(j);
                    equals = true;
                }
                if(equals)
                    break;
            }
        }

        for(int i=0; i<newImages.size(); i++){
            ContentValues vals = new ContentValues();
            vals.put(mDb.OFFER_ID, id);
            vals.put(mDb.CONTENT, newImages.get(i));
            vals.put(mDb.IS_MAIN, false);
            db.insert(mDb.IMAGES, null, vals);
        }

        return id;
    }


}
