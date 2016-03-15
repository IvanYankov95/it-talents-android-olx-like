package model;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

import model.dao.DBOfferDAO;
import model.dao.DBUserDAO;

/**
 * Created by owner on 01/03/2016.
 */
public class OfferManager {

    private static OfferManager instance;
    private DBOfferDAO offerDAO;

    private OfferManager(Context context){
        offerDAO = new DBOfferDAO(context);
    }

    public static OfferManager getInstance(Context context){
        if(instance == null)
            instance = new OfferManager(context);
        return instance;
    }

    public long addOffer(Offer offer, long userId, String category){
        return offerDAO.addOffer(offer, userId, category);
    }

    public ArrayList<String> getAllCategories(){
        return offerDAO.getAllCategories();
    }

    public ArrayList<Offer> getOffersByWord(String word){
        return offerDAO.getOffers(word);
    }

    public Offer getOfferByID(long id) {
        return offerDAO.getOffer(id);
    }

    public ArrayList<Offer> getOffersByCategory(String category){
        return offerDAO.getOffersByCategory(category);
    }

    public ArrayList<Offer> getOffersByUser(long userId){
        return offerDAO.getOffersByUser(userId);
    }

    public int getOffersCount(){
        return offerDAO.getOffersCount();
    }
}
