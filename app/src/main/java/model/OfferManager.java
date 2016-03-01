package model;

import android.content.Context;

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
}
